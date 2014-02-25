package org.multibit.exchange.domain.model;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.multibit.exchange.testing.OrderDescriptorFaker;

import static org.fest.assertions.api.Assertions.assertThat;

public class SecurityOrderTest {

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Test
  public void decreasedBy_Zero() {
    // Arrange
    ItemQuantity orderQuantity = new ItemQuantity("100");
    ItemQuantity decreaseBy = new ItemQuantity("0");
    SecurityOrder limitOrder = OrderDescriptorFaker.createValidLimitOrder().withQty(orderQuantity.getRaw()).toSecurityOrder();
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("cannot decrease by zero");

    // Act
    limitOrder.decreasedBy(decreaseBy);
  }

  @Test
  public void decreasedBy_LessThanQuantity() {
    // Arrange
    ItemQuantity orderQuantity = new ItemQuantity("100");
    ItemQuantity decreaseBy = new ItemQuantity("40.5");
    ItemQuantity expectedUnfilledQuantity = new ItemQuantity("59.5");
    SecurityOrder limitOrder = OrderDescriptorFaker.createValidLimitOrder().withQty(orderQuantity.getRaw()).toSecurityOrder();

    // Act
    SecurityOrder decreasedOrder = limitOrder.decreasedBy(decreaseBy);

    // Assert
    assertThat(decreasedOrder.getOriginalQuantity()).isEqualTo(orderQuantity);
    assertThat(decreasedOrder.getQuantity()).isEqualTo(expectedUnfilledQuantity);
    assertThat(decreasedOrder.getQuantityFilled()).isEqualTo(decreaseBy);
  }

  @Test
  public void decreasedBy_SmallestQuantity() {
    // Arrange
    ItemQuantity orderQuantity = new ItemQuantity("100");
    ItemQuantity decreaseBy = new ItemQuantity("0.00000001");
    ItemQuantity expectedUnfilledQuantity = new ItemQuantity("99.99999999");
    SecurityOrder limitOrder = OrderDescriptorFaker.createValidLimitOrder().withQty(orderQuantity.getRaw()).toSecurityOrder();

    // Act
    SecurityOrder decreasedOrder = limitOrder.decreasedBy(decreaseBy);

    // Assert
    assertThat(decreasedOrder.getOriginalQuantity()).isEqualTo(orderQuantity);
    assertThat(decreasedOrder.getQuantity()).isEqualTo(expectedUnfilledQuantity);
    assertThat(decreasedOrder.getQuantityFilled()).isEqualTo(decreaseBy);
  }
}
