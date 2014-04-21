package org.multibit.exchange.domain.model;

import com.google.common.collect.Maps;
import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;
import org.axonframework.eventsourcing.annotation.EventSourcedMember;
import org.multibit.exchange.domain.event.CurrencyPairRegisteredEvent;
import org.multibit.exchange.domain.event.CurrencyPairRemovedEvent;
import org.multibit.exchange.domain.event.ExchangeCreatedEvent;
import org.multibit.exchange.infrastructure.adaptor.eventapi.CreateExchangeCommand;
import org.multibit.exchange.infrastructure.adaptor.eventapi.CurrencyId;
import org.multibit.exchange.infrastructure.adaptor.eventapi.CurrencyPairId;
import org.multibit.exchange.infrastructure.adaptor.eventapi.ExchangeId;
import org.multibit.exchange.infrastructure.adaptor.eventapi.OrderDescriptor;
import org.multibit.exchange.infrastructure.adaptor.eventapi.OrderFactory;
import org.multibit.exchange.infrastructure.adaptor.eventapi.PlaceOrderCommand;
import org.multibit.exchange.infrastructure.adaptor.eventapi.RegisterCurrencyPairCommand;
import org.multibit.exchange.infrastructure.adaptor.eventapi.RemoveCurrencyPairCommand;

import java.util.Map;

/**
 * <p>AggregateRoot to provide the following to the domain model:</p>
 * <ul>
 * <li>An Event sourced exchange.</li>
 * </ul>
 *
 * @since 0.0.1
 */
public class Exchange extends AbstractAnnotatedAggregateRoot {

  @EventSourcedMember
  private Map<CurrencyPairId, MatchingEngine> matchingEngineMap = Maps.newHashMap();

  @AggregateIdentifier
  private ExchangeId exchangeId;

  /**
   * No-arg constructor required by Axon Framework.
   */
  @SuppressWarnings("unused")
  public Exchange() {
  }

  /*
   * Create Exchange
   */
  @CommandHandler
  @SuppressWarnings("unused")
  public Exchange(CreateExchangeCommand command) {
    apply(new ExchangeCreatedEvent(command.getExchangeId()));
  }

  @EventHandler
  public void on(ExchangeCreatedEvent event) {
    exchangeId = event.getExchangeId();
  }


  /*
   * Register Currency Pair
   */
  @CommandHandler
  @SuppressWarnings("unused")
  void registerCurrencyPair(RegisterCurrencyPairCommand command) throws DuplicateCurrencyPairSymbolException {
    checkForDuplicateCurrencyPair(command.getCurrencyPairId());

    apply(new CurrencyPairRegisteredEvent(exchangeId, command.getCurrencyPairId(), command.getBaseCurrencyId(), command.getCounterCurrencyId()));
  }

  private void checkForDuplicateCurrencyPair(CurrencyPairId symbol) throws DuplicateCurrencyPairSymbolException {
    if (matchingEngineMap.containsKey(symbol)) {
      throw new DuplicateCurrencyPairSymbolException(symbol);
    }
  }

  @EventHandler
  public void on(CurrencyPairRegisteredEvent event) throws DuplicateCurrencyPairSymbolException {
    CurrencyPairId currencyPairId = event.getCurrencyPairId();
    matchingEngineMap.put(currencyPairId, createMatchingEngineForCurrencyPair(currencyPairId, event.getBaseCurrencyId(), event.getCounterCurrencyId()));
  }

  private MatchingEngine createMatchingEngineForCurrencyPair(CurrencyPairId currencyPairId, CurrencyId baseCurrency, CurrencyId counterCurrency) {
    return new MatchingEngine(exchangeId, currencyPairId, baseCurrency, counterCurrency);
  }


  /*
   * Remove Currency Pair
   */
  @CommandHandler
  @SuppressWarnings("unused")
  private void removeCurrencyPair(RemoveCurrencyPairCommand command) throws NoSuchCurrencyPairException {
    validate(command);
    apply(new CurrencyPairRemovedEvent(exchangeId, command.getCurrencyPairId()));
  }

  private void validate(RemoveCurrencyPairCommand command) throws NoSuchCurrencyPairException {
    if (!matchingEngineMap.containsKey(command.getCurrencyPairId())) {
      throw new NoSuchCurrencyPairException(command.getCurrencyPairId());
    }
  }

  @EventHandler
  public void on(CurrencyPairRemovedEvent event) {
    matchingEngineMap.remove(event.getCurrencyPairId());
  }


  /*
   * Place Order
   */
  @CommandHandler
  @SuppressWarnings("unused")
  public void placeOrder(PlaceOrderCommand command) throws NoSuchCurrencyPairException {
    OrderDescriptor orderDescriptor = command.getOrderDescriptor();
    Order order = OrderFactory.createOrderFromDescriptor(orderDescriptor);
    CurrencyPairId currencyPairId = new CurrencyPairId(order.getTicker().getSymbol());
    if (!matchingEngineMap.containsKey(currencyPairId)) {
      throw new NoSuchCurrencyPairException(currencyPairId);
    }

    matchingEngineMap.get(new CurrencyPairId(order.getTicker().getSymbol())).acceptOrder(order);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Exchange exchange = (Exchange) o;

    if (exchangeId != null ? !exchangeId.equals(exchange.exchangeId) : exchange.exchangeId != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    return exchangeId != null ? exchangeId.hashCode() : 0;
  }
}
