package org.multibit.exchange.domain.model;

import com.google.common.base.Optional;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.multibit.exchange.domain.command.CreateExchangeCommand;
import org.multibit.exchange.testing.ExchangeIdFaker;
import org.multibit.exchange.testing.OrderDescriptorFaker;
import org.multibit.exchange.testing.SideFaker;
import org.multibit.exchange.testing.TickerFaker;
import org.multibit.exchange.testing.TradeFaker;

import static org.fest.assertions.api.Assertions.assertThat;

@Ignore
public class OrderBookTest {

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  private ExchangeId exchangeId = ExchangeIdFaker.createValid();
  private Ticker ticker = TickerFaker.createValid();
  private OrderBook orderBook;

  @Before
  public void setUp() {
    Exchange exchange = new Exchange(new CreateExchangeCommand(exchangeId));

    MatchingEngine engine = new MatchingEngine(exchangeId, ticker);
    engine.registerAggregateRoot(exchange);

    orderBook = new OrderBook(exchangeId, ticker, SideFaker.createValid());
    orderBook.registerAggregateRoot(exchange);
  }

  @Test
  public void createNullSide() {
    // Arrange
    Side side = null;
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("side must not be null");

    // Act
    new OrderBook(exchangeId, ticker, side);

  }

  @Test
  public void createBuySide() {
    // Arrange
    Side side = Side.BUY;

    // Act
    OrderBook orderBook = new OrderBook(exchangeId, ticker, side);

    // Assert
    assertThat(orderBook).isNotNull();
    assertThat(orderBook.getOrders()).isEmpty();
    assertThat(orderBook.getTop()).isEqualTo(Optional.<SecurityOrder>absent());
  }

  @Test
  public void createSellSide() {
    // Arrange
    Side side = Side.SELL;

    // Act
    OrderBook orderBook = new OrderBook(exchangeId, ticker, side);

    // Assert
    assertThat(orderBook).isNotNull();
    assertThat(orderBook.getOrders()).isEmpty();
    assertThat(orderBook.getTop()).isEqualTo(Optional.<SecurityOrder>absent());
  }

  @Test
  public void addOrder_LimitOrder() {
    // Arrange
    OrderBook orderBook = new OrderBook(exchangeId, ticker, SideFaker.createValid());
    SecurityOrder expectedOrder = OrderDescriptorFaker.createValidLimitOrder().toSecurityOrder();

    // Act
    orderBook.add(expectedOrder);

    // Assert
    assertThat(orderBook.getTop()).isEqualTo(Optional.of(expectedOrder));
    assertThat(orderBook.getOrders().size()).isEqualTo(1);
    assertThat(orderBook.getOrders().get(0)).isEqualTo(expectedOrder);
  }

  @Test
  public void addOrder_MarketOrder() {
    // Arrange
    OrderBook orderBook = new OrderBook(exchangeId, ticker, SideFaker.createValid());
    SecurityOrder expectedOrder = OrderDescriptorFaker.createValidMarketOrder().toSecurityOrder();

    // Act
    orderBook.add(expectedOrder);

    // Assert
    assertThat(orderBook.getTop()).isEqualTo(Optional.of(expectedOrder));
    assertThat(orderBook.getOrders().size()).isEqualTo(1);
    assertThat(orderBook.getOrders().get(0)).isEqualTo(expectedOrder);
  }

  @Test
  public void addOrders_MarketOrder_Then_LimitOrder() {
    // Arrange
    OrderBook orderBook = new OrderBook(exchangeId, ticker, SideFaker.createValid());
    SecurityOrder expectedMarketOrder = OrderDescriptorFaker.createValidMarketOrder().toSecurityOrder();
    SecurityOrder expectedLimitOrder = OrderDescriptorFaker.createValidLimitOrder().toSecurityOrder();
    orderBook.add(expectedMarketOrder);

    // Act
    orderBook.add(expectedLimitOrder);

    // Assert
    assertThat(orderBook.getTop()).isEqualTo(Optional.of(expectedMarketOrder));
    assertThat(orderBook.getOrders().size()).isEqualTo(2);
    assertThat(orderBook.getOrders().get(0)).isEqualTo(expectedMarketOrder);
    assertThat(orderBook.getOrders().get(1)).isEqualTo(expectedLimitOrder);
  }

  @Test
  public void addOrders_LimitOrder_Then_MarketOrder() {
    // Arrange
    OrderBook orderBook = new OrderBook(exchangeId, ticker, SideFaker.createValid());
    SecurityOrder expectedMarketOrder = OrderDescriptorFaker.createValidMarketOrder().toSecurityOrder();
    SecurityOrder expectedLimitOrder = OrderDescriptorFaker.createValidLimitOrder().toSecurityOrder();
    orderBook.add(expectedLimitOrder);

    // Act
    orderBook.add(expectedMarketOrder);

    // Assert
    assertThat(orderBook.getTop()).isEqualTo(Optional.of(expectedMarketOrder));
    assertThat(orderBook.getOrders().size()).isEqualTo(2);
    assertThat(orderBook.getOrders().get(0)).isEqualTo(expectedMarketOrder);
    assertThat(orderBook.getOrders().get(1)).isEqualTo(expectedLimitOrder);
  }

  @Test
  public void decreaseTopBy_Null() {
    // Arrange
    SecurityOrder order = OrderDescriptorFaker.createValidLimitOrder().withQty("10").toSecurityOrder();
    orderBook.add(order);
    Trade trade = TradeFaker.createValidWithQuantity(null);
    thrown.expect(NullPointerException.class);
    thrown.expectMessage("quantity must not be null");

    // Act
    orderBook.decreaseTopByTradeQuantity(trade);

    // Assert

  }

  @Test
  public void decreaseTopBy_Zero() {
    // Arrange
    SecurityOrder order = OrderDescriptorFaker.createValidLimitOrder().withQty("10").toSecurityOrder();
    orderBook.add(order);
    ItemQuantity zero = new ItemQuantity("0");
    Trade trade = TradeFaker.createValidWithQuantity(zero);
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("quantity must be greater than zero");

    // Act
    orderBook.decreaseTopByTradeQuantity(trade);

    // Assert

  }

  @Test
  public void decreaseTopBy_LessThanUnmatchedQuantity() {
    // Arrange
    ItemQuantity expectedOriginalQuantity = new ItemQuantity("10");
    ItemQuantity decreaseByQuantity = new ItemQuantity("1");
    Trade trade = TradeFaker.createValidWithQuantity(decreaseByQuantity);

    SecurityOrder order = OrderDescriptorFaker.createValidLimitOrder().withQty(expectedOriginalQuantity.getRaw()).toSecurityOrder();
    orderBook.add(order);

    // Act
    orderBook.decreaseTopByTradeQuantity(trade);

    // Assert
    assertThat(orderBook.getTop().get().getInitialQuantity()).isEqualTo(expectedOriginalQuantity);
    assertThat(orderBook.getTop().get().getFilledQuantity()).isEqualTo(decreaseByQuantity);
  }

  @Test
  public void decreaseTopBy_JustLessThanUnmatchedQuantity() {
    // Arrange
    ItemQuantity expectedOriginalQuantity = new ItemQuantity("10");
    ItemQuantity decreaseByQuantity = new ItemQuantity("9.99999999");
    Trade trade = TradeFaker.createValidWithQuantity(decreaseByQuantity);

    SecurityOrder order = OrderDescriptorFaker.createValidLimitOrder().withQty(expectedOriginalQuantity.getRaw()).toSecurityOrder();
    orderBook.add(order);

    // Act
    orderBook.decreaseTopByTradeQuantity(trade);

    // Assert
    assertThat(orderBook.getTop().get().getInitialQuantity()).isEqualTo(expectedOriginalQuantity);
    assertThat(orderBook.getTop().get().getFilledQuantity()).isEqualTo(decreaseByQuantity);
  }

  @Test
  public void decreaseTopBy_EqualToUnmatchedQuantity() {
    // Arrange
    ItemQuantity expectedOriginalQuantity = new ItemQuantity("10");
    ItemQuantity decreaseByQuantity = new ItemQuantity("10");
    Trade trade = TradeFaker.createValidWithQuantity(decreaseByQuantity);

    SecurityOrder order = OrderDescriptorFaker.createValidLimitOrder().withQty(expectedOriginalQuantity.getRaw()).toSecurityOrder();
    orderBook.add(order);

    // Act
    orderBook.decreaseTopByTradeQuantity(trade);

    // Assert
    assertThat(orderBook.getTop().isPresent()).isFalse();
    assertThat(orderBook.getOrders()).isEmpty();
  }

  @Test
  public void decreaseTopBy_GreaterThanUnmatchedQuantity() {
    // Arrange
    ItemQuantity expectedOriginalQuantity = new ItemQuantity("10");
    ItemQuantity decreaseByQuantity = new ItemQuantity("10.00000001");
    Trade trade = TradeFaker.createValidWithQuantity(decreaseByQuantity);

    SecurityOrder order = OrderDescriptorFaker.createValidLimitOrder().withQty(expectedOriginalQuantity.getRaw()).toSecurityOrder();
    orderBook.add(order);

    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("cannot decrease top of orderbook by more than available quantity");

    // Act
    orderBook.decreaseTopByTradeQuantity(trade);
  }

}
