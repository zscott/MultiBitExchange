package org.multibit.exchange.infrastructure.adaptor.web.restapi.readmodel;

import org.joda.time.DateTimeUtils;
import org.junit.Before;
import org.junit.Test;
import org.multibit.common.DateUtils;
import org.multibit.exchange.domain.model.LimitOrder;
import org.multibit.exchange.domain.model.Side;
import org.multibit.exchange.infrastructure.adaptor.eventapi.OrderDescriptor;
import org.multibit.exchange.infrastructure.adaptor.eventapi.OrderFactory;
import org.multibit.exchange.testing.ExchangeIdFaker;
import org.multibit.exchange.testing.OrderDescriptorFaker;
import org.multibit.exchange.testing.TickerFaker;

import static org.fest.assertions.api.Assertions.assertThat;

public class QuoteReadModelTest {

  private String exchangeId;
  private String ticker;
  private String timestamp;
  private OrderBookReadModel bidsOrderBook;
  private OrderBookReadModel asksOrderBook;

  @Before
  public void setUp() {
    exchangeId = ExchangeIdFaker.createValid().getIdentifier();
    ticker = TickerFaker.createValid().getSymbol();
    bidsOrderBook = new OrderBookReadModel(Side.BUY);
    asksOrderBook = new OrderBookReadModel(Side.SELL);

    DateTimeUtils.setCurrentMillisFixed(DateUtils.nowUtc().getMillis());
    timestamp = DateUtils.formatISO8601(DateUtils.nowUtc());
  }

  @Test
  public void createValid() {
    // Arrange

    // Act
    QuoteReadModel quoteReadModel = new QuoteReadModel(exchangeId, ticker, bidsOrderBook, asksOrderBook);

    // Assert
    assertThat(quoteReadModel.getExchangeId()).isEqualTo(exchangeId);
    assertThat(quoteReadModel.getTicker()).isEqualTo(ticker);
    assertThat(quoteReadModel.getBid()).isNull();
    assertThat(quoteReadModel.getAsk()).isNull();
    assertThat(quoteReadModel.getTimestamp()).isEqualTo(timestamp);
  }

  @Test
  public void addBuyOrder() {
    // Arrange
    QuoteReadModel quoteReadModel = new QuoteReadModel(exchangeId, ticker, bidsOrderBook, asksOrderBook);

    String expectedBid = "100.05";
    OrderDescriptor orderDescriptor = OrderDescriptorFaker.createValidLimitOrder().withSide("Buy").withPrice(expectedBid);
    LimitOrder limitOrder = (LimitOrder) OrderFactory.createOrderFromDescriptor(orderDescriptor);

    // Act
    bidsOrderBook.addNewPriceLevel(limitOrder.getLimitPrice(), limitOrder);

    // Assert
    assertThat(quoteReadModel.getExchangeId()).isEqualTo(exchangeId);
    assertThat(quoteReadModel.getTicker()).isEqualTo(ticker);
    assertThat(quoteReadModel.getBid()).isEqualTo(expectedBid);
    assertThat(quoteReadModel.getAsk()).isNull();
    assertThat(quoteReadModel.getSpread()).isNull();
    assertThat(quoteReadModel.getTimestamp()).isEqualTo(timestamp);
  }

  @Test
  public void addSellOrder() {
    // Arrange
    QuoteReadModel quoteReadModel = new QuoteReadModel(exchangeId, ticker, bidsOrderBook, asksOrderBook);

    String expectedAsk = "100.05";
    OrderDescriptor orderDescriptor = OrderDescriptorFaker.createValidLimitOrder().withSide("Sell").withPrice(expectedAsk);
    LimitOrder limitOrder = (LimitOrder) OrderFactory.createOrderFromDescriptor(orderDescriptor);

    // Act
    asksOrderBook.addNewPriceLevel(limitOrder.getLimitPrice(), limitOrder);

    // Assert
    assertThat(quoteReadModel.getExchangeId()).isEqualTo(exchangeId);
    assertThat(quoteReadModel.getTicker()).isEqualTo(ticker);
    assertThat(quoteReadModel.getBid()).isNull();
    assertThat(quoteReadModel.getAsk()).isEqualTo(expectedAsk);
    assertThat(quoteReadModel.getSpread()).isNull();
    assertThat(quoteReadModel.getTimestamp()).isEqualTo(timestamp);
  }

  @Test
  public void addBuyAndSellOrder() {
    // Arrange
    QuoteReadModel quoteReadModel = new QuoteReadModel(exchangeId, ticker, bidsOrderBook, asksOrderBook);

    String expectedBid = "10";
    String expectedAsk = "11";
    String expectedSpread = "1";

    OrderDescriptor buyOrderDescriptor = OrderDescriptorFaker.createValidLimitOrder().withSide("Buy").withPrice(expectedBid);
    LimitOrder buyLimitOrder = (LimitOrder) OrderFactory.createOrderFromDescriptor(buyOrderDescriptor);

    OrderDescriptor sellOrderDescriptor = OrderDescriptorFaker.createValidLimitOrder().withSide("Sell").withPrice(expectedAsk);
    LimitOrder sellLimitOrder = (LimitOrder) OrderFactory.createOrderFromDescriptor(sellOrderDescriptor);

    // Act
    bidsOrderBook.addNewPriceLevel(buyLimitOrder.getLimitPrice(), buyLimitOrder);
    asksOrderBook.addNewPriceLevel(sellLimitOrder.getLimitPrice(), sellLimitOrder);

    // Assert
    assertThat(quoteReadModel.getExchangeId()).isEqualTo(exchangeId);
    assertThat(quoteReadModel.getTicker()).isEqualTo(ticker);
    assertThat(quoteReadModel.getBid()).isEqualTo(expectedBid);
    assertThat(quoteReadModel.getAsk()).isEqualTo(expectedAsk);
    assertThat(quoteReadModel.getSpread()).isEqualTo(expectedSpread);
    assertThat(quoteReadModel.getTimestamp()).isEqualTo(timestamp);
  }

}
