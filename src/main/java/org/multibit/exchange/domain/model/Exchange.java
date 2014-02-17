package org.multibit.exchange.domain.model;

import com.google.common.collect.Maps;
import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;
import org.axonframework.eventsourcing.annotation.EventSourcedMember;
import org.multibit.exchange.domain.command.CreateExchangeCommand;
import org.multibit.exchange.domain.command.PlaceOrderCommand;
import org.multibit.exchange.domain.command.RegisterCurrencyPairCommand;
import org.multibit.exchange.domain.command.RemoveCurrencyPairCommand;
import org.multibit.exchange.domain.event.CurrencyPairRegisteredEvent;
import org.multibit.exchange.domain.event.CurrencyPairRemovedEvent;
import org.multibit.exchange.domain.event.ExchangeCreatedEvent;

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
  private Map<Ticker, MatchingEngine> matchingEngineMap = Maps.newHashMapWithExpectedSize(17);

  @AggregateIdentifier
  private ExchangeId id;

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
    this.id = event.getExchangeId();
  }


  /*
   * Register Currency Pair
   */
  @CommandHandler
  @SuppressWarnings("unused")
  void registerCurrencyPair(RegisterCurrencyPairCommand command) throws DuplicateTickerException {
    validate(command);
    apply(new CurrencyPairRegisteredEvent(id, command.getCurrencyPair()));
  }

  private void validate(RegisterCurrencyPairCommand command) throws DuplicateTickerException {
    CurrencyPair currencyPair = command.getCurrencyPair();
    Ticker ticker = currencyPair.getTicker();
    if (matchingEngineMap.containsKey(ticker))
      throw new DuplicateTickerException(ticker);
  }

  @EventHandler
  public void on(CurrencyPairRegisteredEvent event) throws DuplicateTickerException {
    CurrencyPair currencyPair = event.getCurrencyPair();
    Ticker ticker = currencyPair.getTicker();

    matchingEngineMap.put(ticker, matchingEngineFor(ticker));
  }

  private MatchingEngine matchingEngineFor(Ticker ticker) {
    return new MatchingEngine(ticker, new OrderBook(Side.BUY), new OrderBook(Side.SELL));
  }


  /*
   * Remove Currency Pair
   */
  @CommandHandler
  @SuppressWarnings("unused")
  private void removeCurrencyPair(RemoveCurrencyPairCommand command) throws NoSuchTickerException {
    validate(command);
    apply(new CurrencyPairRemovedEvent(id, command.getCurrencyPair()));
  }

  private void validate(RemoveCurrencyPairCommand command) throws NoSuchTickerException {
    CurrencyPair currencyPair = command.getCurrencyPair();
    Ticker ticker = currencyPair.getTicker();

    if (!matchingEngineMap.containsKey(ticker)) {
      throw new NoSuchTickerException(ticker);
    }
  }

  @EventHandler
  public void on(CurrencyPairRemovedEvent event) {
    CurrencyPair currencyPair = event.getCurrencyPair();
    Ticker ticker = currencyPair.getTicker();

    matchingEngineMap.remove(ticker);
  }


  /*
   * Place Order
   */
  @CommandHandler
  @SuppressWarnings("unused")
  public void placeOrder(PlaceOrderCommand command) throws DuplicateOrderException, NoSuchTickerException {
    validate(command);
    SecurityOrder order = command.getOrder();
    Ticker ticker = order.getTicker();
    matchingEngineMap.get(ticker).acceptOrder(order);
  }

  private void validate(PlaceOrderCommand command) throws NoSuchTickerException {
    SecurityOrder order = command.getOrder();
    Ticker ticker = order.getTicker();
    if (!matchingEngineMap.containsKey(ticker))
      throw new NoSuchTickerException(ticker);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Exchange that = (Exchange) o;

    if (!id.equals(that.id)) return false;

    return true;
  }

  @Override
  public int hashCode() {
    return id.hashCode();
  }

}
