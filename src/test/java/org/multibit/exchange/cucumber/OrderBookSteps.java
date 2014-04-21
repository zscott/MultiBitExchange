package org.multibit.exchange.cucumber;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.fest.util.Lists;
import org.multibit.exchange.domain.model.ItemPrice;
import org.multibit.exchange.domain.model.ItemQuantity;
import org.multibit.exchange.domain.model.LimitOrder;
import org.multibit.exchange.domain.model.MarketOrder;
import org.multibit.exchange.domain.model.Order;
import org.multibit.exchange.domain.model.OrderBook;
import org.multibit.exchange.domain.model.Side;
import org.multibit.exchange.domain.model.Ticker;
import org.multibit.exchange.infrastructure.adaptor.eventapi.CurrencyPairId;
import org.multibit.exchange.infrastructure.adaptor.eventapi.ExchangeId;
import org.multibit.exchange.infrastructure.adaptor.eventapi.OrderId;
import org.multibit.exchange.testing.ExchangeIdFaker;
import org.multibit.exchange.testing.TickerFaker;

import java.util.List;

import static junit.framework.Assert.assertEquals;

public class OrderBookSteps {

  private ExchangeId exchangeId = ExchangeIdFaker.createValid();
  private Ticker ticker = TickerFaker.createValid();
  private CurrencyPairId currencyPairId = new CurrencyPairId(ticker.getSymbol());
  private OrderBook buyBook = new OrderBook(exchangeId, currencyPairId, Side.BUY);
  private OrderBook sellBook = new OrderBook(exchangeId, currencyPairId, Side.SELL);

  @When("^the following orders are added to the \"([^\"]*)\" order book:$")
  public void the_following_orders_are_added_to_the_book(String sideString, List<OrderBookEntryRow> orderBookEntryRows) throws Throwable {
    OrderBook book = getBook(sideString);
    Side side = getSide(sideString);
    List<Order> newOrders = Lists.newArrayList();
    for (OrderBookEntryRow orderBookEntryRow : orderBookEntryRows) {

      OrderId orderId = new OrderId();
      ItemQuantity quantity = new ItemQuantity(orderBookEntryRow.qty);
      if (orderBookEntryRow.price.equals(MarketOrder.MARKET_PRICE)) {
        newOrders.add(new MarketOrder(orderId, orderBookEntryRow.broker, side, quantity, ticker));
      } else {
        newOrders.add(new LimitOrder(orderId, orderBookEntryRow.broker, side, quantity, ticker, new ItemPrice(orderBookEntryRow.price)));
      }
    }
    for (Order order : newOrders) {
      book.add(order);
    }
  }

  @Then("^the \"([^\"]*)\" order book looks like:$")
  public void the_order_book_looks_like(String sideString, List<OrderBookEntryRow> expectedBook) throws Throwable {
    OrderBook book = getBook(sideString);
    List<OrderBookEntryRow> actualBook = Lists.newArrayList();
    List<Order> orders = book.getOrders();
    for (Order order : orders) {
      actualBook.add(new OrderBookEntryRow(order.getBroker(), order.getUnfilledQuantity().getRaw(), order.getBookDisplay()));
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

