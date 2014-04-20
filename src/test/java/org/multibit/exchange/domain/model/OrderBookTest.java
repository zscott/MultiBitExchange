package org.multibit.exchange.domain.model;

import com.google.common.base.Optional;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.multibit.exchange.infrastructure.adaptor.eventapi.CreateExchangeCommand;
import org.multibit.exchange.infrastructure.adaptor.eventapi.CurrencyId;
import org.multibit.exchange.infrastructure.adaptor.eventapi.CurrencyPairId;
import org.multibit.exchange.infrastructure.adaptor.eventapi.ExchangeId;
import org.multibit.exchange.infrastructure.adaptor.eventapi.SecurityOrderFactory;
import org.multibit.exchange.testing.CurrencyPairFaker;
import org.multibit.exchange.testing.ExchangeIdFaker;
import org.multibit.exchange.testing.OrderDescriptorFaker;
import org.multibit.exchange.testing.SideFaker;
import org.multibit.exchange.testing.TradeFaker;

import static org.fest.assertions.api.Assertions.assertThat;

@Ignore
public class OrderBookTest {

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  private ExchangeId exchangeId = ExchangeIdFaker.createValid();
  private CurrencyPair currencyPair = CurrencyPairFaker.createValid();
  private CurrencyPairId currencyPairId = new CurrencyPairId(currencyPair.getSymbol());
  private OrderBook orderBook;
  private CurrencyId baseCurrencyId = new CurrencyId(currencyPair.getBaseCurrency().getSymbol());
  private CurrencyId counterCurrencyId = new CurrencyId(currencyPair.getCounterCurrency().getSymbol());

  @Before
  public void setUp() {
    Exchange exchange = new Exchange(new CreateExchangeCommand(exchangeId));

    MatchingEngine engine = new MatchingEngine(exchangeId, currencyPairId, baseCurrencyId, counterCurrencyId);
    engine.registerAggregateRoot(exchange);

    orderBook = new OrderBook(exchangeId, currencyPairId, SideFaker.createValid());
    orderBook.registerAggregateRoot(exchange);
  }

  @Test
  public void createNullSide() {
    // Arrange
    Side side = null;
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("side must not be null");

    // Act
    new OrderBook(exchangeId, currencyPairId, side);

  }

  @Test
  public void createBuySide() {
    // Arrange
    Side side = Side.BUY;

    // Act
    OrderBook orderBook = new OrderBook(exchangeId, currencyPairId, side);

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
    OrderBook orderBook = new OrderBook(exchangeId, currencyPairId, side);

    // Assert
    assertThat(orderBook).isNotNull();
    assertThat(orderBook.getOrders()).isEmpty();
    assertThat(orderBook.getTop()).isEqualTo(Optional.<SecurityOrder>absent());
  }

  @Test
  public void addOrder_LimitOrder() {
    // Arrange
    OrderBook orderBook = new OrderBook(exchangeId, currencyPairId, SideFaker.createValid());
    SecurityOrder expectedOrder = SecurityOrderFactory.createOrderFromDescriptor(OrderDescriptorFaker.createValidLimitOrder());

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
    OrderBook orderBook = new OrderBook(exchangeId, currencyPairId, SideFaker.createValid());
    SecurityOrder expectedOrder = SecurityOrderFactory.createOrderFromDescriptor(OrderDescriptorFaker.createValidMarketOrder());

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
    OrderBook orderBook = new OrderBook(exchangeId, currencyPairId, SideFaker.createValid());
    SecurityOrder expectedMarketOrder = SecurityOrderFactory.createOrderFromDescriptor(OrderDescriptorFaker.createValidMarketOrder());
    SecurityOrder expectedLimitOrder = SecurityOrderFactory.createOrderFromDescriptor(OrderDescriptorFaker.createValidLimitOrder());
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
    OrderBook orderBook = new OrderBook(exchangeId, currencyPairId, SideFaker.createValid());
    SecurityOrder expectedMarketOrder = SecurityOrderFactory.createOrderFromDescriptor(OrderDescriptorFaker.createValidMarketOrder());
    SecurityOrder expectedLimitOrder = SecurityOrderFactory.createOrderFromDescriptor(OrderDescriptorFaker.createValidLimitOrder());
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
    SecurityOrder order = SecurityOrderFactory.createOrderFromDescriptor(OrderDescriptorFaker.createValidLimitOrder().withQty("10"));
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
    SecurityOrder order = SecurityOrderFactory.createOrderFromDescriptor(OrderDescriptorFaker.createValidLimitOrder().withQty("10"));
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

    SecurityOrder order = SecurityOrderFactory.createOrderFromDescriptor(OrderDescriptorFaker.createValidLimitOrder().withQty(expectedOriginalQuantity.getRaw()));
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

    SecurityOrder order = SecurityOrderFactory.createOrderFromDescriptor(OrderDescriptorFaker.createValidLimitOrder().withQty(expectedOriginalQuantity.getRaw()));
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

    SecurityOrder order = SecurityOrderFactory.createOrderFromDescriptor(OrderDescriptorFaker.createValidLimitOrder().withQty(expectedOriginalQuantity.getRaw()));
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

    SecurityOrder order = SecurityOrderFactory.createOrderFromDescriptor(OrderDescriptorFaker.createValidLimitOrder().withQty(expectedOriginalQuantity.getRaw()));
    orderBook.add(order);

    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("cannot decrease top of orderbook by more than available quantity");

    // Act
    orderBook.decreaseTopByTradeQuantity(trade);
  }

}
