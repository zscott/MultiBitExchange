package org.multibit.exchange.cucumber;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.fest.util.Lists;
import org.multibit.exchange.domainmodel.*;
import org.multibit.exchange.testing.SecurityOrderIdFaker;

import java.util.List;

import static junit.framework.Assert.assertEquals;

public class OrderBookSteps {

  private Ticker ticker = new Ticker("BTCUSD");
  private OrderBook buyBook = new OrderBook(Side.BUY);
  private OrderBook sellBook = new OrderBook(Side.SELL);

  @When("^the following orders are added to the \"([^\"]*)\" order book:$")
  public void the_following_orders_are_added_to_the_book(String sideString, List<OrderBookEntryRow> orderBookEntryRows) throws Throwable {
    OrderBook book = getBook(sideString);
    Side side = getSide(sideString);
    List<SecurityOrder> newOrders = Lists.newArrayList();
    for (OrderBookEntryRow orderBookEntryRow : orderBookEntryRows) {

      SecurityOrderId orderId = SecurityOrderIdFaker.nextId();
      ItemQuantity quantity = new ItemQuantity(orderBookEntryRow.qty);
      if (orderBookEntryRow.price.equals("M")) {
        newOrders.add(new MarketOrder(orderId, orderBookEntryRow.broker, side, quantity, ticker));
      } else {
        newOrders.add(new LimitOrder(orderId, orderBookEntryRow.broker, side, quantity, ticker, new ItemPrice(orderBookEntryRow.price)));
      }
    }
    for (SecurityOrder order : newOrders) {
      book.add(order);
    }
  }

  @Then("^the \"([^\"]*)\" order book looks like:$")
  public void the_order_book_looks_like(String sideString, List<OrderBookEntryRow> expectedBook) throws Throwable {
    OrderBook book = getBook(sideString);
    List<OrderBookEntryRow> actualBook = Lists.newArrayList();
    List<SecurityOrder> orders = book.getOrders();
    for (SecurityOrder order : orders) {
      actualBook.add(new OrderBookEntryRow(order.getBroker(), order.getQuantity().getRaw(), order.getBookDisplay()));
    }
    assertEquals(expectedBook, actualBook);
  }

  private Side getSide(String sideString) {
    return Side.valueOf(sideString.toUpperCase());
  }

  private OrderBook getBook(String sideString) {
    if (getSide(sideString) == Side.BUY)
      return buyBook;
    else
      return sellBook;
  }
}

