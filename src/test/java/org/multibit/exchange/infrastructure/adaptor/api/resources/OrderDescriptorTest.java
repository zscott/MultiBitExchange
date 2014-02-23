package org.multibit.exchange.infrastructure.adaptor.api.resources;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.multibit.exchange.domain.model.LimitOrder;
import org.multibit.exchange.domain.model.MarketOrder;
import org.multibit.exchange.domain.model.SecurityOrder;
import org.multibit.exchange.testing.OrderDescriptorFaker;

import static org.fest.assertions.api.Assertions.assertThat;

public class OrderDescriptorTest {

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Test
  public void testToSecurityOrder_ValidLimitOrder() {
    // Arrange
    OrderDescriptor descriptor = OrderDescriptorFaker.createValidLimitOrder();
    String expectedBroker = descriptor.getBroker();
    String expectedSide = descriptor.getSide().toUpperCase();
    String expectedQty = descriptor.getQty();
    String expectedTicker = descriptor.getTicker();
    String expectedPrice = descriptor.getPrice();

    // Act
    SecurityOrder securityOrder = descriptor.toSecurityOrder();

    // Assert
    assertLimitOrder(securityOrder, expectedBroker, expectedSide, expectedQty, expectedTicker, expectedPrice);
  }

  @Test
  public void testToSecurityOrder_LimitOrder_NullBroker() {
    // Arrange
    OrderDescriptor descriptor = OrderDescriptorFaker.createValidLimitOrder().withBroker(null);
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("broker must not be null or empty");

    // Act
    descriptor.toSecurityOrder();

    // Assert
  }

  @Test
  public void testToSecurityOrder_LimitOrder_EmptyBroker() {
    // Arrange
    OrderDescriptor descriptor = OrderDescriptorFaker.createValidLimitOrder().withBroker("");
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("broker must not be null or empty");

    // Act
    descriptor.toSecurityOrder();

    // Assert
  }

  @Test
  public void testToSecurityOrder_LimitOrder_NullSide() {
    // Arrange
    OrderDescriptor descriptor = OrderDescriptorFaker.createValidLimitOrder().withSide(null);
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("side must not be null or empty");

    // Act
    descriptor.toSecurityOrder();

    // Assert
  }

  @Test
  public void testToSecurityOrder_LimitOrder_EmptySide() {
    // Arrange
    OrderDescriptor descriptor = OrderDescriptorFaker.createValidLimitOrder().withSide("");
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("side must not be null or empty");

    // Act
    descriptor.toSecurityOrder();

    // Assert
  }

  @Test
  public void testToSecurityOrder_LimitOrder_WhitespaceSide() {
    // Arrange
    OrderDescriptor descriptor = OrderDescriptorFaker.createValidLimitOrder().withSide("  ");
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("side must be BUY or SELL");

    // Act
    descriptor.toSecurityOrder();

    // Assert
  }

  @Test
  public void testToSecurityOrder_LimitOrder_LowerCaseSellSide() {
    // Arrange
    OrderDescriptor descriptor = OrderDescriptorFaker.createValidLimitOrder().withSide("sell");

    // Act
    descriptor.toSecurityOrder();

    // Assert
  }

  @Test
  public void testToSecurityOrder_LimitOrder_MixedCaseSellSide() {
    // Arrange
    OrderDescriptor descriptor = OrderDescriptorFaker.createValidLimitOrder().withSide("SeLl");

    // Act
    descriptor.toSecurityOrder();

    // Assert
  }

  @Test
  public void testToSecurityOrder_LimitOrder_NotBuyOrSellSide() {
    // Arrange
    OrderDescriptor descriptor = OrderDescriptorFaker.createValidLimitOrder().withSide("wrong");
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("side must be BUY or SELL");

    // Act
    descriptor.toSecurityOrder();

    // Assert
  }

  @Test
  public void testToSecurityOrder_LimitOrder_LowerCaseBuySide() {
    // Arrange
    OrderDescriptor descriptor = OrderDescriptorFaker.createValidLimitOrder().withSide("buy");

    // Act
    descriptor.toSecurityOrder();

    // Assert
  }

  @Test
  public void testToSecurityOrder_LimitOrder_MixedCaseBuySide() {
    // Arrange
    OrderDescriptor descriptor = OrderDescriptorFaker.createValidLimitOrder().withSide("BuY");

    // Act
    descriptor.toSecurityOrder();

    // Assert
  }

  @Test
  public void testToSecurityOrder_LimitOrder_AnotherNotBuyOrSellSide() {
    // Arrange
    OrderDescriptor descriptor = OrderDescriptorFaker.createValidLimitOrder().withSide("wrong again");
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("side must be BUY or SELL");

    // Act
    descriptor.toSecurityOrder();

    // Assert
  }

  @Test
  public void testToSecurityOrder_LimitOrder_NullQuantity() {
    // Arrange
    OrderDescriptor descriptor = OrderDescriptorFaker.createValidLimitOrder().withQty(null);
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("quantity must not be null or empty");

    // Act
    descriptor.toSecurityOrder();

    // Assert
  }

  @Test
  public void testToSecurityOrder_LimitOrder_EmptyQuantity() {
    // Arrange
    OrderDescriptor descriptor = OrderDescriptorFaker.createValidLimitOrder().withQty("");
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("quantity must not be null or empty");

    // Act
    descriptor.toSecurityOrder();

    // Assert
  }

  @Test
  public void testToSecurityOrder_LimitOrder_ZeroQuantity() {
    // Arrange
    OrderDescriptor descriptor = OrderDescriptorFaker.createValidLimitOrder().withQty("0");
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("quantity must not be zero");

    // Act
    descriptor.toSecurityOrder();

    // Assert
  }

  @Test
  public void testToSecurityOrder_LimitOrder_NegativeQuantity() {
    // Arrange
    OrderDescriptor descriptor = OrderDescriptorFaker.createValidLimitOrder().withQty("-10");
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("quantity must not be negative");

    // Act
    descriptor.toSecurityOrder();

    // Assert
  }

  @Test
  public void testToSecurityOrder_LimitOrder_NullTicker() {
    // Arrange
    OrderDescriptor descriptor = OrderDescriptorFaker.createValidLimitOrder().withTicker(null);
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("ticker symbol must not be null or empty");

    // Act
    descriptor.toSecurityOrder();

    // Assert
  }

  @Test
  public void testToSecurityOrder_LimitOrder_EmptyTicker() {
    // Arrange
    OrderDescriptor descriptor = OrderDescriptorFaker.createValidLimitOrder().withTicker("");
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("ticker symbol must not be null or empty");

    // Act
    descriptor.toSecurityOrder();

    // Assert
  }

  @Test
  public void testToSecurityOrder_LimitOrder_WhitespaceTicker() {
    // Arrange
    OrderDescriptor descriptor = OrderDescriptorFaker.createValidLimitOrder().withTicker("   ");
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("ticker symbol must not be null or empty");

    // Act
    descriptor.toSecurityOrder();

    // Assert
  }

  @Test
  public void testToSecurityOrder_LimitOrder_NullPrice() {
    // Arrange
    OrderDescriptor descriptor = OrderDescriptorFaker.createValidLimitOrder().withPrice(null);
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("price must not be null or empty");

    // Act
    descriptor.toSecurityOrder();

    // Assert
  }

  @Test
  public void testToSecurityOrder_LimitOrder_EmptyPrice() {
    // Arrange
    OrderDescriptor descriptor = OrderDescriptorFaker.createValidLimitOrder().withPrice("");
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("price must not be null or empty");

    // Act
    descriptor.toSecurityOrder();

    // Assert
  }

  @Test
  public void testToSecurityOrder_LimitOrder_WhitespacePrice() {
    // Arrange
    OrderDescriptor descriptor = OrderDescriptorFaker.createValidLimitOrder().withPrice("  ");
    thrown.expect(NumberFormatException.class);

    // Act
    descriptor.toSecurityOrder();

    // Assert
  }

  @Test
  public void testToSecurityOrder_MarketPrice1() {
    // Arrange
    OrderDescriptor descriptor = OrderDescriptorFaker.createValidLimitOrder().withPrice(MarketOrder.MARKET_PRICE);

    // Act
    descriptor.toSecurityOrder();

    // Assert
  }

  @Test
  public void testToSecurityOrder_MarketPrice2() {
    // Arrange
    OrderDescriptor descriptor = OrderDescriptorFaker.createValidMarketOrder();

    // Act
    descriptor.toSecurityOrder();

    // Assert
  }

  @Test
  public void testToSecurityOrder_InvalidPriceCharacter() {
    // Arrange
    OrderDescriptor descriptor = OrderDescriptorFaker.createValidMarketOrder().withPrice("L");
    thrown.expect(NumberFormatException.class);

    // Act
    descriptor.toSecurityOrder();

    // Assert
  }

  @Test
  public void testToSecurityOrder_ValidMarketOrder() {
    // Arrange
    OrderDescriptor descriptor = OrderDescriptorFaker.createValidMarketOrder();
    String expectedBroker = descriptor.getBroker();
    String expectedSide = descriptor.getSide().toUpperCase();
    String expectedQty = descriptor.getQty();
    String expectedTicker = descriptor.getTicker();

    // Act
    SecurityOrder securityOrder = descriptor.toSecurityOrder();

    // Assert
    assertMarketOrder(securityOrder, expectedBroker, expectedSide, expectedQty, expectedTicker);
  }

  private void assertMarketOrder(SecurityOrder securityOrder, String expectedBroker, String expectedSide, String expectedQty, String expectedTicker) {
    assertThat(securityOrder).isInstanceOf(MarketOrder.class);
    assertThat(securityOrder.getBroker()).isEqualTo(expectedBroker);
    assertThat(securityOrder.getOriginalQuantity().getRaw()).isEqualTo(expectedQty);
    assertThat(securityOrder.getSide().toString()).isEqualTo(expectedSide);
    assertThat(securityOrder.getTicker().getSymbol()).isEqualTo(expectedTicker);
  }

  private void assertLimitOrder(SecurityOrder securityOrder, String expectedBroker, String expectedSide, String expectedQty, String expectedTicker, String expectedPrice) {
    assertThat(securityOrder).isInstanceOf(LimitOrder.class);
    assertThat(((LimitOrder) securityOrder).getLimitPrice().getRaw()).isEqualTo(expectedPrice);
    assertThat(securityOrder.getBroker()).isEqualTo(expectedBroker);
    assertThat(securityOrder.getOriginalQuantity().getRaw()).isEqualTo(expectedQty);
    assertThat(securityOrder.getSide().toString()).isEqualTo(expectedSide);
    assertThat(securityOrder.getTicker().getSymbol()).isEqualTo(expectedTicker);
  }
}
