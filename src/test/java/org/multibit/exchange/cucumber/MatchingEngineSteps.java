package org.multibit.exchange.cucumber;

import com.google.common.collect.Lists;
import com.google.common.eventbus.Subscribe;
import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.multibit.exchange.domainmodel.*;
import org.multibit.exchange.infrastructure.adaptor.events.GuavaEventBusEventPublisher;
import org.multibit.exchange.testing.SecurityOrderIdFaker;

import java.util.List;

import static junit.framework.Assert.assertEquals;

public class MatchingEngineSteps {

  private Ticker ticker = new Ticker("TEST");

  private OrderBook buyBook = new OrderBook(Side.BUY);
  private OrderBook sellBook = new OrderBook(Side.SELL);
  private EventPublisher eventPublisher = new GuavaEventBusEventPublisher();
  private final EventRecorder eventRecorder = new EventRecorder();
  private MatchingEngine engine = new MatchingEngine(ticker, buyBook, sellBook, eventPublisher);

  public MatchingEngineSteps() {
    eventPublisher.register(eventRecorder);
  }

  @When("^the following orders are submitted:$")
  public void the_following_orders_are_submitted_in_this_order(List<OrderRow> orderRows) throws Throwable {
    for (OrderRow orderRow : orderRows) {
      SecurityOrderId id = SecurityOrderIdFaker.nextId();
      String broker = orderRow.broker;
      Side side = Side.valueOf(orderRow.side.toUpperCase());
      ItemQuantity qty = new ItemQuantity(orderRow.qty);
      if (orderRow.price.equals("M")) { // Market Order
        engine.acceptOrder(new MarketOrder(id, broker, side, qty, ticker));
      } else {
        ItemPrice limitPrice = new ItemPrice(orderRow.price);
        engine.acceptOrder(new LimitOrder(id, broker, side, qty, ticker, limitPrice));
      }
    }
  }

  @Then("^the following trades are generated:$")
  public void the_following_trades_are_generated(List<TradeRow> expectedTrades) throws Throwable {
    assertEquals(expectedTrades, eventRecorder.getTrades());
  }

  @And("^market order book looks like:$")
  public void market_order_book_looks_like(DataTable dataTable) throws Throwable {
    // Express the Regexp above with the code you wish you had
    //throw new PendingException();
  }

  private class EventRecorder {

    private List<TradeRow> trades = Lists.newArrayList();

    @Subscribe public void recordTrade(Trade trade) {
      trades.add(new TradeRow(trade.getBuySideBroker(), trade.getSellSideBroker(), trade.getQuantity().getRaw(), trade.getPrice().getRaw()));
    }

    public List<TradeRow> getTrades() {
      return trades;
    }
  }
}
