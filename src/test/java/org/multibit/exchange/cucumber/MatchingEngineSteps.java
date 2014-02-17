package org.multibit.exchange.cucumber;

import com.google.common.collect.Lists;
import cucumber.api.DataTable;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.multibit.exchange.domain.command.PlaceOrderCommand;
import org.multibit.exchange.domain.command.RegisterCurrencyPairCommand;
import org.multibit.exchange.domain.model.*;
import org.multibit.exchange.testing.AxonMatchingEngineTestFixture;
import org.multibit.exchange.testing.MatchingEngineTestFixture;
import org.multibit.exchange.testing.SecurityOrderIdFaker;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MatchingEngineSteps {

  private Currency baseCurrency = new Currency("BaseCCY");
  private Currency counterCurrency = new Currency("CounterCCY");
  private CurrencyPair pair = new CurrencyPair(baseCurrency, counterCurrency);
  private Ticker ticker = pair.getTicker();

  private MatchingEngineTestFixture fixture;

  @Before
  public void setUp() {
    fixture = new AxonMatchingEngineTestFixture();
    fixture.given(
        new RegisterCurrencyPairCommand(fixture.getExchangeId(), pair)
    );
  }

  @When("^the following orders are submitted:$")
  public void the_following_orders_are_submitted_in_this_order(List<OrderRow> orderRows) throws Throwable {
    List<PlaceOrderCommand> commands = Lists.newArrayListWithCapacity(orderRows.size());
    for (OrderRow orderRow : orderRows) {
      SecurityOrderId id = SecurityOrderIdFaker.nextId();
      String broker = orderRow.broker;
      Side side = Side.valueOf(orderRow.side.toUpperCase());
      ItemQuantity qty = new ItemQuantity(orderRow.qty);
      if (orderRow.price.equals(MarketOrder.MARKET_PRICE)) { // Market Order
        commands.add(new PlaceOrderCommand(fixture.getExchangeId(), new MarketOrder(id, broker, side, qty, ticker)));
      } else {
        ItemPrice limitPrice = new ItemPrice(orderRow.price);
        commands.add(new PlaceOrderCommand(fixture.getExchangeId(), new LimitOrder(id, broker, side, qty, ticker, limitPrice)));
      }
    }
    for (PlaceOrderCommand command : commands) {
      fixture.when(command);
    }
  }

  @Then("^the following trades are generated:$")
  public void the_following_trades_are_generated(List<TradeRow> expectedTrades) throws Throwable {
    assertEquals(expectedTrades, fixture.getObservedTrades());
    fixture.resetObservations();
  }

  @And("^market order book looks like:$")
  public void market_order_book_looks_like(DataTable dataTable) throws Throwable {
    List<OrderRow> expectedBuyOrders = extractExpectedBuyOrders(dataTable);
    List<OrderRow> expectedSellOrders = extractExpectedSellOrders(dataTable);

    List<OrderRow> actualBuyOrders = Lists.newArrayList();
    List<OrderRow> actualSellOrders = Lists.newArrayList();

    for (SecurityOrder order : fixture.getOrderBook(Side.BUY).getOrders()) {
      actualBuyOrders.add(orderRowFromOrder(order));
    }

    for (SecurityOrder order : fixture.getOrderBook(Side.SELL).getOrders()) {
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
    assertTrue(fixture.getObservedTrades().isEmpty());
  }

}
