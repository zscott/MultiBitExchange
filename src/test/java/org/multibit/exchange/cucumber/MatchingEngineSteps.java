package org.multibit.exchange.cucumber;

import com.google.common.collect.Lists;
import com.google.common.eventbus.Subscribe;
import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.multibit.common.DomainEvents;
import org.multibit.exchange.domainmodel.*;
import org.multibit.exchange.infrastructure.adaptor.events.GuavaEventBusEventPublisher;
import org.multibit.exchange.testing.SecurityOrderIdFaker;

import java.util.LinkedList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MatchingEngineSteps {

  private Ticker ticker = new Ticker("TEST");

  private OrderBook buyBook = new OrderBook(Side.BUY);
  private OrderBook sellBook = new OrderBook(Side.SELL);
  private final EventRecorder eventRecorder = new EventRecorder();
  private MatchingEngine engine = new MatchingEngine(ticker, buyBook, sellBook);

  public MatchingEngineSteps() {
    DomainEvents.register(eventRecorder);
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
    eventRecorder.reset();
  }

  @And("^market order book looks like:$")
  public void market_order_book_looks_like(DataTable dataTable) throws Throwable {
    List<OrderRow> expectedBuyOrders = extractExpectedBuyOrders(dataTable);
    List<OrderRow> expectedSellOrders = extractExpectedSellOrders(dataTable);

    List<OrderRow> actualBuyOrders = Lists.newArrayList();
    List<OrderRow> actualSellOrders = Lists.newArrayList();

    for (SecurityOrder order : buyBook.getOrders()) {
      actualBuyOrders.add(orderRowFromOrder(order));
    }

    for (SecurityOrder order : sellBook.getOrders()) {
      actualSellOrders.add(orderRowFromOrder(order));
    }

    assertEquals(expectedBuyOrders, actualBuyOrders);
    assertEquals(expectedSellOrders, actualSellOrders);
  }

  private List<OrderRow> extractExpectedBuyOrders(DataTable dataTable) {
    List<OrderRow> orderRows = Lists.newArrayList();
    List<List<String>> raw = dataTable.raw();

    for (List<String> row : raw.subList(1, raw.size())) {
      // broker, size, price
      String broker = row.get(0);
      String size = row.get(1);
      String price = row.get(2);
      if (!(broker.isEmpty() || size.isEmpty() || price.isEmpty())) {
        orderRows.add(new OrderRow(broker, "Buy", size, price));
      }
    }

    return orderRows;
  }

  private List<OrderRow> extractExpectedSellOrders(DataTable dataTable) {
    List<OrderRow> orderRows = Lists.newArrayList();
    List<List<String>> raw = dataTable.raw();

    for (List<String> row : raw.subList(1, raw.size())) {
      // broker, size, price
      String broker = row.get(5);
      String size = row.get(4);
      String price = row.get(3);
      if (!(broker.isEmpty() || size.isEmpty() || price.isEmpty())) {
        orderRows.add(new OrderRow(broker, "Sell", size, price));
      }
    }

    return orderRows;
  }


  private OrderRow orderRowFromOrder(SecurityOrder order) {
    return new OrderRow(
        order.getBroker(),
        order.getSide() == Side.BUY ? "Buy" : "Sell",
        order.getQuantity().getRaw(),
        order.getPriceString());
  }

  @Then("^no trades are generated$")
  public void no_trades_are_generated() throws Throwable {
    assertTrue(eventRecorder.getTrades().isEmpty());
  }

  private class EventRecorder {

    private LinkedList<TradeRow> trades = Lists.newLinkedList();

    @Subscribe
    public void recordTrade(Trade trade) {
      trades.add(new TradeRow(trade.getBuySideBroker(), trade.getSellSideBroker(), trade.getQuantity().getRaw(), trade.getPrice().getRaw()));
    }

    public List<TradeRow> getTrades() {
      return trades;
    }

    public void reset() {
      trades = Lists.newLinkedList();
    }
  }
}
