package org.multibit.exchange.cucumber;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import cucumber.api.DataTable;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.multibit.exchange.domain.command.CurrencyPairDescriptor;
import org.multibit.exchange.domain.command.OrderDescriptor;
import org.multibit.exchange.domain.model.SecurityOrder;
import org.multibit.exchange.domain.model.Side;
import org.multibit.exchange.infrastructure.adaptor.web.restapi.readmodel.QuoteReadModel;
import org.multibit.exchange.testing.EventBasedExchangeServiceTestFixture;
import org.multibit.exchange.testing.MatchingEngineTestFixture;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

public class MatchingEngineSteps {

  private String baseCurrency = "BaseCCY";
  private String counterCurrency = "CounteryCCY";
  private CurrencyPairDescriptor currencyPairDescriptor = new CurrencyPairDescriptor(baseCurrency, counterCurrency);
  private String tickerSymbol = currencyPairDescriptor.getTickerSymbol();
  private MatchingEngineTestFixture fixture;

  @Before
  public void setUp() {
    fixture = new EventBasedExchangeServiceTestFixture();
    fixture.registerCurrencyPair(currencyPairDescriptor);
  }

  @When("^the following orders are submitted:$")
  public void the_following_orders_are_submitted_in_this_order(List<OrderRow> orderRows) throws Throwable {
    for (OrderRow orderRow : orderRows) {
      OrderDescriptor order
          = new OrderDescriptor(orderRow.broker, orderRow.side, orderRow.qty, tickerSymbol, orderRow.price);
      fixture.placeOrder(order);
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

    for (SecurityOrder order : fixture.getOrderBookReadModel(Side.BUY).getOpenOrders()) {
      actualBuyOrders.add(orderRowFromOrder(order));
    }

    for (SecurityOrder order : fixture.getOrderBookReadModel(Side.SELL).getOpenOrders()) {
      actualSellOrders.add(orderRowFromOrder(order));
    }

    assertEquals(expectedBuyOrders, actualBuyOrders);
    assertEquals(expectedSellOrders, actualSellOrders);
  }

  @And("^the quote looks like:$")
  public void quote_looks_like(DataTable dataTable) throws Throwable {
    QuoteRow expectedQuote = extractExpectedQuotes(dataTable);
    QuoteReadModel quoteReadModel = fixture.getQuoteReadModel();

    assertThat(quoteReadModel.getAsk()).isEqualTo(expectedQuote.getAsk());
    assertThat(quoteReadModel.getBid()).isEqualTo(expectedQuote.getBid());
    assertThat(quoteReadModel.getSpread()).isEqualTo(expectedQuote.getSpread());
  }

  private QuoteRow extractExpectedQuotes(DataTable dataTable) {
    List<List<String>> raw = dataTable.raw();
    Preconditions.checkState(raw.size() == 2, "Quotes tables currently only support one quote.");

    List<String> row = raw.get(1);
    // Bid, Ask, Spread
    String bid = row.get(0);
    String ask = row.get(1);
    String spread = row.get(2);
    return new QuoteRow(bid, ask, spread);
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
        order.getUnfilledQuantity().getRaw(),
        order.getPriceString());
  }

  @Then("^no trades are generated$")
  public void no_trades_are_generated() throws Throwable {
    assertTrue(fixture.getObservedTrades().isEmpty());
  }

}
