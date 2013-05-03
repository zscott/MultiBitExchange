package org.multibit.exchange.infrastructure.adaptor.events;

import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;
import org.multibit.exchange.domainmodel.Exchange;
import org.multibit.exchange.domainmodel.ExchangeId;
import org.multibit.exchange.domainmodel.Ticker;
import org.multibit.exchange.domainmodel.TradeableItem;
import org.multibit.exchange.domainmodel.TradeablePair;

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
  }




  /*
   * Command Handlers
   */
  @CommandHandler
  public ExchangeAggregateRoot(CreateExchangeCommand command) {
    apply(new ExchangeCreatedEvent(command.getExchangeId()));
  }

  @CommandHandler
  void createSecurity(CreateSecurityCommand command) {
    apply(new SecurityCreatedEvent(
      command.getExchangeId(),
      command.getTicker(),
      command.getTradeableItem(),
      command.getCurrency()));
  }

  @CommandHandler
  public void placeBidOrder(PlaceBidOrderCommand command) {
    apply(new BidOrderPlacedEvent(command.getExchangeId(), command.getQuantity()));
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
  public void on(SecurityCreatedEvent event) {
    Ticker ticker = new Ticker(event.getTickerSymbol());
    TradeablePair tradeablePair = new TradeablePair(
      new TradeableItem(event.getTradeableItemSymbol()),
      new TradeableItem(event.getCurrencySymbol()));

    exchange.addSecurity(ticker, tradeablePair);
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
}
