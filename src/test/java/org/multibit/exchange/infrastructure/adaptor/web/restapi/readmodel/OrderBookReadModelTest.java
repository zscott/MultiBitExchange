package org.multibit.exchange.infrastructure.adaptor.web.restapi.readmodel;

import org.junit.Test;
import org.multibit.exchange.domain.model.ItemPrice;
import org.multibit.exchange.domain.model.ItemQuantity;
import org.multibit.exchange.domain.model.LimitOrder;
import org.multibit.exchange.domain.model.Side;
import org.multibit.exchange.infrastructure.adaptor.eventapi.OrderFactory;
import org.multibit.exchange.testing.OrderDescriptorFaker;
import org.multibit.exchange.testing.SideFaker;

import java.math.BigDecimal;

import static org.fest.assertions.api.Assertions.assertThat;

public class OrderBookReadModelTest {

  @Test
  public void createBuySide() {
    // Arrange
    Side side = Side.BUY;

    // Act
    OrderBookReadModel orderBookReadModel = new OrderBookReadModel(side);

    // Assert
    assertThat(orderBookReadModel).isNotNull();
    assertThat(orderBookReadModel.getOpenOrders()).isEmpty();
    assertThat(orderBookReadModel.getPriceLevels()).isEmpty();
  }

  @Test
  public void createSellSide() {
    // Arrange
    Side side = Side.SELL;

    // Act
    OrderBookReadModel orderBookReadModel = new OrderBookReadModel(side);

    // Assert
    assertThat(orderBookReadModel).isNotNull();
    assertThat(orderBookReadModel.getOpenOrders()).isEmpty();
    assertThat(orderBookReadModel.getPriceLevels()).isEmpty();
  }

  @Test
  public void addNewPriceLevel() {
    // Arrange
    Side side = SideFaker.createValid();
    OrderBookReadModel orderBookReadModel = new OrderBookReadModel(side);
    ItemPrice priceLevel = new ItemPrice("567.32");
    ItemQuantity quantity = new ItemQuantity("55.4444");

    LimitOrder order = (LimitOrder) OrderFactory.createOrderFromDescriptor(OrderDescriptorFaker
        .createValidLimitOrder()
        .withPrice(priceLevel.getRaw())
        .withQty(quantity.getRaw()));

    int expectedOpenOrders = 1;
    int expectedPriceLevels = 1;

    // Act
    orderBookReadModel.addNewPriceLevel(priceLevel, order);

    // Assert
    assertThat(orderBookReadModel.getOpenOrders().size()).isEqualTo(expectedOpenOrders);
    assertThat(orderBookReadModel.getPriceLevels().size()).isEqualTo(expectedPriceLevels);
    assertThat(orderBookReadModel.getUnfilledQuantity(priceLevel)).isEqualTo(quantity.getQuantity());
  }

  @Test
  public void removePriceLevel_OnlyOneLevelExists() {
    // Arrange
    Side side = SideFaker.createValid();
    OrderBookReadModel orderBookReadModel = new OrderBookReadModel(side);
    ItemPrice priceLevel = new ItemPrice("567.32");
    ItemQuantity quantity = new ItemQuantity("55.4444");

    LimitOrder order = (LimitOrder) OrderFactory.createOrderFromDescriptor(OrderDescriptorFaker
        .createValidLimitOrder()
        .withPrice(priceLevel.getRaw())
        .withQty(quantity.getRaw()));

    int expectedOpenOrders = 0;
    int expectedPriceLevels = 0;

    orderBookReadModel.addNewPriceLevel(priceLevel, order);

    // Act
    orderBookReadModel.removePriceLevel(priceLevel);

    // Assert
    assertThat(orderBookReadModel.getOpenOrders().size()).isEqualTo(expectedOpenOrders);
    assertThat(orderBookReadModel.getPriceLevels().size()).isEqualTo(expectedPriceLevels);
    assertThat(orderBookReadModel.getUnfilledQuantity(priceLevel)).isEqualTo(BigDecimal.ZERO);
  }

  @Test
  public void removePriceLevel_TwoLevelsExist() {
    // Arrange
    Side side = SideFaker.createValid();
    OrderBookReadModel orderBookReadModel = new OrderBookReadModel(side);
    ItemPrice priceLevel1 = new ItemPrice("567.32");
    ItemQuantity quantity1 = new ItemQuantity("55.4444");
    LimitOrder order1 = (LimitOrder) OrderFactory.createOrderFromDescriptor(OrderDescriptorFaker
        .createValidLimitOrder()
        .withPrice(priceLevel1.getRaw())
        .withQty(quantity1.getRaw()));

    ItemPrice priceLevel2 = new ItemPrice("7000");
    ItemQuantity quantity2 = new ItemQuantity("700");
    LimitOrder order2 = (LimitOrder) OrderFactory.createOrderFromDescriptor(OrderDescriptorFaker
        .createValidLimitOrder()
        .withPrice(priceLevel2.getRaw())
        .withQty(quantity2.getRaw()));

    int expectedOpenOrders = 1;
    int expectedPriceLevels = 1;

    orderBookReadModel.addNewPriceLevel(order1.getLimitPrice(), order1);
    orderBookReadModel.addNewPriceLevel(order2.getLimitPrice(), order2);

    // Act
    orderBookReadModel.removePriceLevel(priceLevel1);

    // Assert
    assertThat(orderBookReadModel.getOpenOrders().size()).isEqualTo(expectedOpenOrders);
    assertThat(orderBookReadModel.getPriceLevels().size()).isEqualTo(expectedPriceLevels);
  }
}
