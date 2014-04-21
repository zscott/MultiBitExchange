package org.multibit.exchange.infrastructure.adaptor.web.restapi.resources;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.multibit.exchange.domain.model.LimitOrder;
import org.multibit.exchange.domain.model.MarketOrder;
import org.multibit.exchange.domain.model.Order;
import org.multibit.exchange.infrastructure.adaptor.eventapi.OrderDescriptor;
import org.multibit.exchange.infrastructure.adaptor.eventapi.OrderFactory;
import org.multibit.exchange.testing.OrderDescriptorFaker;

import static org.fest.assertions.api.Assertions.assertThat;

public class OrderDescriptorTest {

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Test
  public void testCreateOrderFromDescriptor_ValidLimitOrder() {
    // Arrange
    OrderDescriptor descriptor = OrderDescriptorFaker.createValidLimitOrder();
    String expectedBroker = descriptor.getBroker();
    String expectedSide = descriptor.getSide().toUpperCase();
    String expectedQty = descriptor.getQty();
    String expectedTicker = descriptor.getTicker();
    String expectedPrice = descriptor.getPrice();

    // Act
    Order order = OrderFactory.createOrderFromDescriptor(descriptor);

    // Assert
    assertLimitOrder(order, expectedBroker, expectedSide, expectedQty, expectedTicker, expectedPrice);
  }

  @Test
  public void testCreateOrderFromDescriptor_LimitOrder_NullBroker() {
    // Arrange
    OrderDescriptor descriptor = OrderDescriptorFaker.createValidLimitOrder().withBroker(null);
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("broker must not be null or empty");

    // Act
    OrderFactory.createOrderFromDescriptor(descriptor);

    // Assert
  }

  @Test
  public void testCreateOrderFromDescriptor_LimitOrder_EmptyBroker() {
    // Arrange
    OrderDescriptor descriptor = OrderDescriptorFaker.createValidLimitOrder().withBroker("");
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("broker must not be null or empty");

    // Act
    OrderFactory.createOrderFromDescriptor(descriptor);

    // Assert
  }

  @Test
  public void testCreateOrderFromDescriptor_LimitOrder_NullSide() {
    // Arrange
    OrderDescriptor descriptor = OrderDescriptorFaker.createValidLimitOrder().withSide(null);
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("side must not be null or empty");

    // Act
    OrderFactory.createOrderFromDescriptor(descriptor);

    // Assert
  }

  @Test
  public void testCreateOrderFromDescriptor_LimitOrder_EmptySide() {
    // Arrange
    OrderDescriptor descriptor = OrderDescriptorFaker.createValidLimitOrder().withSide("");
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("side must not be null or empty");

    // Act
    OrderFactory.createOrderFromDescriptor(descriptor);

    // Assert
  }

  @Test
  public void testCreateOrderFromDescriptor_LimitOrder_WhitespaceSide() {
    // Arrange
    OrderDescriptor descriptor = OrderDescriptorFaker.createValidLimitOrder().withSide("  ");
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("side must be BUY or SELL");

    // Act
    OrderFactory.createOrderFromDescriptor(descriptor);

    // Assert
  }

  @Test
  public void testCreateOrderFromDescriptor_LimitOrder_LowerCaseSellSide() {
    // Arrange
    OrderDescriptor descriptor = OrderDescriptorFaker.createValidLimitOrder().withSide("sell");

    // Act
    OrderFactory.createOrderFromDescriptor(descriptor);

    // Assert
  }

  @Test
  public void testCreateOrderFromDescriptor_LimitOrder_MixedCaseSellSide() {
    // Arrange
    OrderDescriptor descriptor = OrderDescriptorFaker.createValidLimitOrder().withSide("SeLl");

    // Act
    OrderFactory.createOrderFromDescriptor(descriptor);

    // Assert
  }

  @Test
  public void testCreateOrderFromDescriptor_LimitOrder_NotBuyOrSellSide() {
    // Arrange
    OrderDescriptor descriptor = OrderDescriptorFaker.createValidLimitOrder().withSide("wrong");
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("side must be BUY or SELL");

    // Act
    OrderFactory.createOrderFromDescriptor(descriptor);

    // Assert
  }

  @Test
  public void testCreateOrderFromDescriptor_LimitOrder_LowerCaseBuySide() {
    // Arrange
    OrderDescriptor descriptor = OrderDescriptorFaker.createValidLimitOrder().withSide("buy");

    // Act
    OrderFactory.createOrderFromDescriptor(descriptor);

    // Assert
  }

  @Test
  public void testCreateOrderFromDescriptor_LimitOrder_MixedCaseBuySide() {
    // Arrange
    OrderDescriptor descriptor = OrderDescriptorFaker.createValidLimitOrder().withSide("BuY");

    // Act
    OrderFactory.createOrderFromDescriptor(descriptor);

    // Assert
  }

  @Test
  public void testCreateOrderFromDescriptor_LimitOrder_AnotherNotBuyOrSellSide() {
    // Arrange
    OrderDescriptor descriptor = OrderDescriptorFaker.createValidLimitOrder().withSide("wrong again");
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("side must be BUY or SELL");

    // Act
    OrderFactory.createOrderFromDescriptor(descriptor);

    // Assert
  }

  @Test
  public void testCreateOrderFromDescriptor_LimitOrder_NullQuantity() {
    // Arrange
    OrderDescriptor descriptor = OrderDescriptorFaker.createValidLimitOrder().withQty(null);
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("quantity must not be null or empty");

    // Act
    OrderFactory.createOrderFromDescriptor(descriptor);

    // Assert
  }

  @Test
  public void testCreateOrderFromDescriptor_LimitOrder_EmptyQuantity() {
    // Arrange
    OrderDescriptor descriptor = OrderDescriptorFaker.createValidLimitOrder().withQty("");
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("quantity must not be null or empty");

    // Act
    OrderFactory.createOrderFromDescriptor(descriptor);

    // Assert
  }

  @Test
  public void testCreateOrderFromDescriptor_LimitOrder_ZeroQuantity() {
    // Arrange
    OrderDescriptor descriptor = OrderDescriptorFaker.createValidLimitOrder().withQty("0");
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("quantity must not be zero");

    // Act
    OrderFactory.createOrderFromDescriptor(descriptor);

    // Assert
  }

  @Test
  public void testCreateOrderFromDescriptor_LimitOrder_NegativeQuantity() {
    // Arrange
    OrderDescriptor descriptor = OrderDescriptorFaker.createValidLimitOrder().withQty("-10");
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("quantity must not be negative");

    // Act
    OrderFactory.createOrderFromDescriptor(descriptor);

    // Assert
  }

  @Test
  public void testCreateOrderFromDescriptor_LimitOrder_NullTicker() {
    // Arrange
    OrderDescriptor descriptor = OrderDescriptorFaker.createValidLimitOrder().forCurrencyPair(null);
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("ticker symbol must not be null or empty");

    // Act
    OrderFactory.createOrderFromDescriptor(descriptor);

    // Assert
  }

  @Test
  public void testCreateOrderFromDescriptor_LimitOrder_EmptyTicker() {
    // Arrange
    OrderDescriptor descriptor = OrderDescriptorFaker.createValidLimitOrder().forCurrencyPair("");
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("ticker symbol must not be null or empty");

    // Act
    OrderFactory.createOrderFromDescriptor(descriptor);

    // Assert
  }

  @Test
  public void testCreateOrderFromDescriptor_LimitOrder_WhitespaceTicker() {
    // Arrange
    OrderDescriptor descriptor = OrderDescriptorFaker.createValidLimitOrder().forCurrencyPair("   ");
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("ticker symbol must not be null or empty");

    // Act
    OrderFactory.createOrderFromDescriptor(descriptor);

    // Assert
  }

  @Test
  public void testCreateOrderFromDescriptor_LimitOrder_NullPrice() {
    // Arrange
    OrderDescriptor descriptor = OrderDescriptorFaker.createValidLimitOrder().withPrice(null);
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("price must not be null or empty");

    // Act
    OrderFactory.createOrderFromDescriptor(descriptor);

    // Assert
  }

  @Test
  public void testCreateOrderFromDescriptor_LimitOrder_EmptyPrice() {
    // Arrange
    OrderDescriptor descriptor = OrderDescriptorFaker.createValidLimitOrder().withPrice("");
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("price must not be null or empty");

    // Act
    OrderFactory.createOrderFromDescriptor(descriptor);

    // Assert
  }

  @Test
  public void testCreateOrderFromDescriptor_LimitOrder_WhitespacePrice() {
    // Arrange
    OrderDescriptor descriptor = OrderDescriptorFaker.createValidLimitOrder().withPrice("  ");
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("price must be '" + MarketOrder.MARKET_PRICE + "' for Market Orders or a number for Limit Orders");

    // Act
    OrderFactory.createOrderFromDescriptor(descriptor);

    // Assert
  }

  @Test
  public void testCreateOrderFromDescriptor_MarketPrice1() {
    // Arrange
    OrderDescriptor descriptor = OrderDescriptorFaker.createValidLimitOrder().withPrice(MarketOrder.MARKET_PRICE);

    // Act
    OrderFactory.createOrderFromDescriptor(descriptor);

    // Assert
  }

  @Test
  public void testCreateOrderFromDescriptor_MarketPrice2() {
    // Arrange
    OrderDescriptor descriptor = OrderDescriptorFaker.createValidMarketOrder();

    // Act
    OrderFactory.createOrderFromDescriptor(descriptor);

    // Assert
  }

  @Test
  public void testCreateOrderFromDescriptor_InvalidPriceCharacter() {
    // Arrange
    OrderDescriptor descriptor = OrderDescriptorFaker.createValidMarketOrder().withPrice("_");
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("price must be '" + MarketOrder.MARKET_PRICE + "' for Market Orders or a number for Limit Orders");

    // Act
    OrderFactory.createOrderFromDescriptor(descriptor);

    // Assert
  }

  @Test
  public void testCreateOrderFromDescriptor_ValidMarketOrder() {
    // Arrange
    OrderDescriptor descriptor = OrderDescriptorFaker.createValidMarketOrder();
    String expectedBroker = descriptor.getBroker();
    String expectedSide = descriptor.getSide().toUpperCase();
    String expectedQty = descriptor.getQty();
    String expectedTicker = descriptor.getTicker();

    // Act
    Order order = OrderFactory.createOrderFromDescriptor(descriptor);

    // Assert
    assertMarketOrder(order, expectedBroker, expectedSide, expectedQty, expectedTicker);
  }

  private void assertMarketOrder(Order order, String expectedBroker, String expectedSide, String expectedQty, String expectedTicker) {
    assertThat(order).isInstanceOf(MarketOrder.class);
    assertThat(order.getBroker()).isEqualTo(expectedBroker);
    assertThat(order.getInitialQuantity().getRaw()).isEqualTo(expectedQty);
    assertThat(order.getSide().toString()).isEqualTo(expectedSide);
    assertThat(order.getTicker().getSymbol()).isEqualTo(expectedTicker);
  }

  private void assertLimitOrder(Order order, String expectedBroker, String expectedSide, String expectedQty, String expectedTicker, String expectedPrice) {
    assertThat(order).isInstanceOf(LimitOrder.class);
    assertThat(((LimitOrder) order).getLimitPrice().getRaw()).isEqualTo(expectedPrice);
    assertThat(order.getBroker()).isEqualTo(expectedBroker);
    assertThat(order.getInitialQuantity().getRaw()).isEqualTo(expectedQty);
    assertThat(order.getSide().toString()).isEqualTo(expectedSide);
    assertThat(order.getTicker().getSymbol()).isEqualTo(expectedTicker);
  }
}
