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
import org.multibit.exchange.infrastructure.adaptor.eventapi.ExchangeId;
import org.multibit.exchange.infrastructure.adaptor.eventapi.OrderDescriptor;
import org.multibit.exchange.infrastructure.adaptor.eventapi.PlaceOrderCommand;
import org.multibit.exchange.infrastructure.adaptor.eventapi.RegisterCurrencyPairCommand;
import org.multibit.exchange.infrastructure.adaptor.eventapi.RemoveTickerCommand;
import org.multibit.exchange.infrastructure.adaptor.eventapi.SecurityOrderFactory;

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
  private Map<String, MatchingEngine> matchingEngineMap = Maps.newHashMap();

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
    checkForDuplicateTicker(command.getSymbol());

    apply(CurrencyPairRegisteredEvent.create(exchangeId, command.getSymbol(), command.getBaseCurrency(), command.getCounterCurrency()));
  }

  private void checkForDuplicateTicker(String symbol) throws DuplicateCurrencyPairSymbolException {
    if (matchingEngineMap.containsKey(symbol)) {
      throw new DuplicateCurrencyPairSymbolException(symbol);
    }
  }

  @EventHandler
  public void on(CurrencyPairRegisteredEvent event) throws DuplicateCurrencyPairSymbolException {
    String symbol = event.getSymbol();
    matchingEngineMap.put(symbol, createMatchingEngineForSymbol(symbol));
  }

  private MatchingEngine createMatchingEngineForSymbol(String symbol) {
    return new MatchingEngine(exchangeId, new Ticker(symbol));
  }


  /*
   * Remove Currency Pair
   */
  @CommandHandler
  @SuppressWarnings("unused")
  private void removeCurrencyPair(RemoveTickerCommand command) throws NoSuchTickerException {
    validate(command);
    apply(new CurrencyPairRemovedEvent(exchangeId, command.getTickerSymbol()));
  }

  private void validate(RemoveTickerCommand command) throws NoSuchTickerException {
    String tickerSymbol = command.getTickerSymbol();
    if (!matchingEngineMap.containsKey(tickerSymbol)) {
      throw new NoSuchTickerException(tickerSymbol);
    }
  }

  @EventHandler
  public void on(CurrencyPairRemovedEvent event) {
    String tickerSymbol = event.getSymbol();
    matchingEngineMap.remove(tickerSymbol);
  }


  /*
   * Place Order
   */
  @CommandHandler
  @SuppressWarnings("unused")
  public void placeOrder(PlaceOrderCommand command) throws NoSuchTickerException {
    OrderDescriptor orderDescriptor = command.getOrderDescriptor();
    SecurityOrder order = SecurityOrderFactory.createOrderFromDescriptor(orderDescriptor);
    Ticker ticker = order.getTicker();
    if (!matchingEngineMap.containsKey(ticker.getSymbol())) {
      throw new NoSuchTickerException(ticker.getSymbol());
    }

    matchingEngineMap.get(ticker.getSymbol()).acceptOrder(order);
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
