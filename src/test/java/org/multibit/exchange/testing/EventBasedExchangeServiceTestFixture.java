package org.multibit.exchange.testing;

import com.google.common.collect.Lists;
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
import org.multibit.exchange.domain.event.OrderAcceptedEvent;
import org.multibit.exchange.domain.event.TradeExecutedEvent;
import org.multibit.exchange.domain.model.*;
import org.multibit.exchange.infrastructure.service.EventBasedExchangeService;

import java.util.LinkedList;
import java.util.List;

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

  private EventBus eventBus;
  private CommandBus commandBus;
  private EventStore eventStore;

  private OrderBook sellBook = new OrderBook(Side.SELL);
  private OrderBook buyBook = new OrderBook(Side.BUY);
  private final EventObserver eventObserver;
  private final AggregateAnnotationCommandHandler commandHandler;
  private final CommandGateway commandGateway;
  private final EventBasedExchangeService exchangeService;

  public EventBasedExchangeServiceTestFixture() {
    eventBus = new SimpleEventBus();
    commandBus = new SimpleCommandBus();
    eventStore = new InMemoryEventStore();
    commandGateway = new DefaultCommandGateway(commandBus);
    exchangeService = new EventBasedExchangeService(commandGateway, commandBus, eventBus);

    AggregateFactory<Exchange> aggregateFactory = new GenericAggregateFactory<>(aggregateType);
    EventSourcingRepository<Exchange> repository
        = new CachingEventSourcingRepository<>(aggregateFactory, eventStore);
    repository.setEventBus(eventBus);

    // register aggregate as command handler
    commandHandler = new AggregateAnnotationCommandHandler<>(aggregateType, repository, commandBus);
    commandHandler.subscribe();

    // register test event recorder
    eventObserver = new EventObserver();
    AnnotationEventListenerAdapter.subscribe(eventObserver, eventBus);

    exchangeId = ExchangeIdFaker.createValid();
    initializeExchange();
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

  public OrderBook getOrderBook(Side side) {
    return (side == Side.SELL) ? sellBook : buyBook;
  }

  @Override
  public void registerCurrencyPair(CurrencyPair pair) {
    exchangeService.registerCurrencyPair(exchangeId, pair);
  }

  private class EventObserver {

    private LinkedList<TradeRow> trades = Lists.newLinkedList();

    @SuppressWarnings("unused")
    @EventHandler
    public void onTradeExecuted(TradeExecutedEvent event) {
      Trade trade = event.getTrade();
      trades.add(new TradeRow(trade.getBuySideBroker(), trade.getSellSideBroker(), trade.getQuantity().getRaw(), trade.getPrice().getRaw()));
      decreaseTopOfCounterbook(event);
    }

    private void decreaseTopOfCounterbook(TradeExecutedEvent event) {
      Trade trade = event.getTrade();
      if (event.getTriggeringSide() == Side.BUY) {
        sellBook.decreaseTopBy(trade.getQuantity());
      } else {
        buyBook.decreaseTopBy(trade.getQuantity());
      }
    }

    @SuppressWarnings("unused")
    @EventHandler
    public void onOrderAccepted(OrderAcceptedEvent event) {
      SecurityOrder order = event.getOrder();
      if (order.getSide() == Side.BUY) {
        buyBook.add(order);
      } else {
        sellBook.add(order);
      }
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
