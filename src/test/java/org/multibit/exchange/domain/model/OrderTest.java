package org.multibit.exchange.domain.model;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.multibit.exchange.infrastructure.adaptor.eventapi.OrderDescriptor;
import org.multibit.exchange.infrastructure.adaptor.eventapi.OrderFactory;
import org.multibit.exchange.testing.BrokerFaker;
import org.multibit.exchange.testing.ItemQuantityFaker;
import org.multibit.exchange.testing.OrderDescriptorFaker;
import org.multibit.exchange.testing.SideFaker;
import org.multibit.exchange.testing.TickerFaker;

import static org.fest.assertions.api.Assertions.assertThat;

public class OrderTest {

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Test
  public void createZeroLimitPriceOrder() {
    // Arrange
    ItemPrice zeroPrice = new ItemPrice("0");
    OrderDescriptor orderDescriptor = new OrderDescriptor(
        BrokerFaker.createValid(),
        SideFaker.createValid().toString(),
        ItemQuantityFaker.createValid().getRaw(),
        TickerFaker.createValid().getSymbol(),
        zeroPrice.getRaw());
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("limit price must be greater than zero");

    // Act
    OrderFactory.createOrderFromDescriptor(orderDescriptor);

    // Assert
  }

  @Test
  public void decreasedBy_Zero() {
    // Arrange
    ItemQuantity orderQuantity = new ItemQuantity("100");
    ItemQuantity decreaseBy = new ItemQuantity("0");
    Order limitOrder = OrderFactory.createOrderFromDescriptor(OrderDescriptorFaker.createValidLimitOrder().withQty(orderQuantity.getRaw()));
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
    Order limitOrder = OrderFactory.createOrderFromDescriptor(OrderDescriptorFaker.createValidLimitOrder().withQty(orderQuantity.getRaw()));

    // Act
    Order decreasedOrder = limitOrder.decreasedBy(decreaseBy);

    // Assert
    assertThat(decreasedOrder.getInitialQuantity()).isEqualTo(orderQuantity);
    assertThat(decreasedOrder.getUnfilledQuantity()).isEqualTo(expectedUnfilledQuantity);
    assertThat(decreasedOrder.getFilledQuantity()).isEqualTo(decreaseBy);
  }

  @Test
  public void decreasedBy_SmallestQuantity() {
    // Arrange
    ItemQuantity orderQuantity = new ItemQuantity("100");
    ItemQuantity decreaseBy = new ItemQuantity("0.00000001");
    ItemQuantity expectedUnfilledQuantity = new ItemQuantity("99.99999999");
    Order limitOrder = OrderFactory.createOrderFromDescriptor(OrderDescriptorFaker.createValidLimitOrder().withQty(orderQuantity.getRaw()));

    // Act
    Order decreasedOrder = limitOrder.decreasedBy(decreaseBy);

    // Assert
    assertThat(decreasedOrder.getInitialQuantity()).isEqualTo(orderQuantity);
    assertThat(decreasedOrder.getUnfilledQuantity()).isEqualTo(expectedUnfilledQuantity);
    assertThat(decreasedOrder.getFilledQuantity()).isEqualTo(decreaseBy);
  }
}
