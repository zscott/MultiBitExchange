package org.multibit.exchange.testing;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.CommandCallback;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.commandhandling.annotation.AggregateAnnotationCommandHandler;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.commandhandling.gateway.DefaultCommandGateway;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.SimpleEventBus;
import org.axonframework.eventhandling.annotation.AnnotationEventListenerAdapter;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.axonframework.eventsourcing.AggregateFactory;
import org.axonframework.eventsourcing.CachingEventSourcingRepository;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventsourcing.GenericAggregateFactory;
import org.axonframework.eventstore.EventStore;
import org.axonframework.test.FixtureExecutionException;
import org.multibit.exchange.cucumber.TradeRow;
import org.multibit.exchange.domain.event.LimitOrderAddedToExistingPriceLevelEvent;
import org.multibit.exchange.domain.event.LimitOrderAddedToNewPriceLevelEvent;
import org.multibit.exchange.domain.event.PriceLevelCompletelyFilledEvent;
import org.multibit.exchange.domain.event.TopOrderCompletelyFilledEvent;
import org.multibit.exchange.domain.event.TopOrderPartiallyFilledEvent;
import org.multibit.exchange.domain.model.CurrencyPair;
import org.multibit.exchange.domain.model.Exchange;
import org.multibit.exchange.domain.model.ExchangeId;
import org.multibit.exchange.domain.model.ItemPrice;
import org.multibit.exchange.domain.model.LimitOrder;
import org.multibit.exchange.domain.model.SecurityOrder;
import org.multibit.exchange.domain.model.Side;
import org.multibit.exchange.domain.model.Ticker;
import org.multibit.exchange.domain.model.Trade;
import org.multibit.exchange.infrastructure.adaptor.web.restapi.readmodel.OrderBookReadModel;
import org.multibit.exchange.infrastructure.service.AxonEventBasedExchangeService;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * <p>Fixture to provide the following to tests:</p>
 * <ul>
 * <li>An easy way to functionally test the ExchangeService and underlying matching engine.</li>
 * </ul>
 *
 * @since 0.0.1
 */
public class EventBasedExchangeServiceTestFixture implements MatchingEngineTestFixture {

  Class<Exchange> aggregateType = Exchange.class;

  private ExchangeId exchangeId;

  private final EventObserver eventObserver;

  private final AxonEventBasedExchangeService exchangeService;

  private Map<Side, OrderBookReadModel> limitBook;


  public EventBasedExchangeServiceTestFixture() {
    EventBus eventBus = new SimpleEventBus();
    CommandBus commandBus = new SimpleCommandBus();
    EventStore eventStore = new InMemoryEventStore();
    CommandGateway commandGateway = new DefaultCommandGateway(commandBus);
    exchangeService = new AxonEventBasedExchangeService(commandGateway);

    AggregateFactory<Exchange> aggregateFactory = new GenericAggregateFactory<>(aggregateType);
    EventSourcingRepository<Exchange> repository
        = new CachingEventSourcingRepository<>(aggregateFactory, eventStore);
    repository.setEventBus(eventBus);

    // register aggregate as command handler
    AggregateAnnotationCommandHandler commandHandler = new AggregateAnnotationCommandHandler<>(aggregateType, repository, commandBus);
    commandHandler.subscribe();

    // register test event recorder
    eventObserver = new EventObserver();
    AnnotationEventListenerAdapter.subscribe(eventObserver, eventBus);

    exchangeId = ExchangeIdFaker.createValid();
    Ticker ticker = TickerFaker.createValid();
    initializeExchange();

    limitBook = Maps.newHashMap();
    limitBook.put(Side.BUY, new OrderBookReadModel(Side.BUY));
    limitBook.put(Side.SELL, new OrderBookReadModel(Side.SELL));
  }

  private void initializeExchange() {
    exchangeService.initializeExchange(exchangeId);
  }

  @Override
  public void placeOrder(SecurityOrder order) {
    exchangeService.placeOrder(exchangeId, order);
  }

  public ExchangeId getExchangeId() {
    return exchangeId;
  }

  public List<TradeRow> getObservedTrades() {
    return eventObserver.getTrades();
  }

  public void resetObservations() {
    eventObserver.reset();
  }

  public OrderBookReadModel getOrderBookReadModel(Side side) {
    return limitBook.get(side);
  }

  @Override
  public void registerCurrencyPair(CurrencyPair currencyPair) {
    exchangeService.registerCurrencyPair(exchangeId, currencyPair);
  }

  private class EventObserver {

    private LinkedList<TradeRow> trades = Lists.newLinkedList();

    @SuppressWarnings("unused")
    @EventHandler
    public void handle(PriceLevelCompletelyFilledEvent event) {
      Trade trade = event.getTrade();
      Side side = event.getSide();
      ItemPrice priceLevel = event.getPriceLevel();
      getOrderBookReadModel(side).removePriceLevel(priceLevel);
      recordTrade(trade);
    }

    @SuppressWarnings("unused")
    @EventHandler
    public void handle(TopOrderPartiallyFilledEvent event) {
      Trade trade = event.getTrade();
      Side side = event.getSide();
      ItemPrice priceLevel = event.getPriceLevel();
      getOrderBookReadModel(side).partialFillTopOrderAtPriceLevel(priceLevel, trade);
      recordTrade(trade);
    }

    @SuppressWarnings("unused")
    @EventHandler
    public void handle(TopOrderCompletelyFilledEvent event) {
      Trade trade = event.getTrade();
      Side side = event.getSide();
      ItemPrice priceLevel = event.getPriceLevel();
      getOrderBookReadModel(side).completelyFillTopOrderAtPriceLevel(priceLevel);
      recordTrade(trade);
    }

    @SuppressWarnings("unused")
    @EventHandler
    public void handle(LimitOrderAddedToExistingPriceLevelEvent event) {
      LimitOrder order = event.getOrder();
      Side side = order.getSide();
      ItemPrice priceLevel = order.getLimitPrice();
      getOrderBookReadModel(side).addOrderAtPriceLevel(priceLevel, order);
    }

    @SuppressWarnings("unused")
    @EventHandler
    public void handle(LimitOrderAddedToNewPriceLevelEvent event) {
      LimitOrder order = event.getOrder();
      Side side = order.getSide();
      ItemPrice priceLevel = order.getLimitPrice();
      getOrderBookReadModel(side).addNewPriceLevel(priceLevel, order);
    }

    private void recordTrade(Trade trade) {
      trades.add(new TradeRow(trade.getBuySideBroker(), trade.getSellSideBroker(), trade.getQuantity().getRaw(), trade.getPrice().getRaw()));
    }

    public List<TradeRow> getTrades() {
      return trades;
    }

    public void reset() {
      trades = Lists.newLinkedList();
    }
  }

  private class ExecutionExceptionAwareCallback implements CommandCallback<Object> {

    private FixtureExecutionException exception;

    @Override
    public void onSuccess(Object result) {
    }

    @Override
    public void onFailure(Throwable cause) {
      if (cause instanceof FixtureExecutionException) {
        this.exception = (FixtureExecutionException) cause;
      }
    }

    public void assertSuccessful() {
      if (exception != null) {
        throw exception;
      }
    }
  }

}
