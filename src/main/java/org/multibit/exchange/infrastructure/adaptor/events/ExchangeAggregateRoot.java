package org.multibit.exchange.infrastructure.adaptor.events;

import com.google.common.eventbus.Subscribe;
import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;
import org.multibit.common.DomainEvents;
import org.multibit.exchange.domainmodel.*;
import org.multibit.exchange.domainmodel.events.OrderAccepted;

/**
 * <p>AggregateRoot to provide the following to the Axon Framework:</p>
 * <ul>
 * <li>An axon-specific representation of the AggregateRoot: {@link org.multibit.exchange.domainmodel.Exchange} in the domain model.</li>
 * <li>Event handling methods for events targeted at this aggregate root.</li>
 * <li>Command handling methods for events targeted at this aggregate root.</li>
 * </ul>
 *
 * @since 0.0.1
 */
public class ExchangeAggregateRoot extends AbstractAnnotatedAggregateRoot {

  @AggregateIdentifier
  private ExchangeId id;

  private Exchange exchange;

  /**
   * No-arg constructor required by Axon Framework.
   */
  public ExchangeAggregateRoot() {
    DomainEvents.register(new DomainEventMapper());
  }


  /*
   * Command Handlers
   */
  @CommandHandler
  public ExchangeAggregateRoot(CreateExchangeCommand command) {
    apply(new ExchangeCreatedEvent(command.getExchangeId()));
  }

  @CommandHandler
  void createSecurity(RegisterCurrencyPairCommand command) {
    apply(new CurrencyPairRegisteredEvent(
        id,
        command.getCurrencyPair()));
  }

  @CommandHandler
  public void placeOrder(PlaceOrderCommand command) throws DuplicateOrderException, NoSuchTickerException {
    exchange.placeOrder(command.getOrder());
  }

  /*
   * Event Handlers
   */
  @EventHandler
  public void on(ExchangeCreatedEvent event) {
    this.id = event.getExchangeId();
    this.exchange = new Exchange();
  }

  @EventHandler
  public void on(CurrencyPairRegisteredEvent event) throws DuplicateTickerException {
    CurrencyPair currencyPair = event.getCurrencyPair();
    exchange.addSecurity(currencyPair.getTicker(), currencyPair);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    ExchangeAggregateRoot that = (ExchangeAggregateRoot) o;

    if (!id.equals(that.id)) return false;

    return true;
  }

  @Override
  public int hashCode() {
    return id.hashCode();
  }

  private class DomainEventMapper {

    //todo - ZS - @Subscribe is a direct dependency on Google EventBus - not ideal
    @Subscribe
    public void handleDomainEvent(OrderAccepted e) {
      apply(new OrderAcceptedEvent(id, e.getOrder()));
    }

  }
}
