package org.multibit.exchange.infrastructure.adaptor.api.resources;

import com.google.common.collect.Lists;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.multibit.common.DateUtils;
import org.multibit.exchange.domainmodel.Currency;
import org.multibit.exchange.domainmodel.Ticker;
import org.multibit.exchange.domainmodel.TradeableItem;
import org.multibit.exchange.infrastructure.adaptor.api.readmodel.OrderListReadModel;
import org.multibit.exchange.infrastructure.adaptor.api.readmodel.OrderReadModel;
import org.multibit.exchange.testing.CurrencyFaker;
import org.multibit.exchange.testing.TickerFaker;
import org.multibit.exchange.testing.TradeableItemFaker;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class OrdersResourceIntegrationTest extends ResourceIntegrationTestBase {

  @Test
  public void GET_orders() {
    // Arrange
    final String orderId = UUID.randomUUID().toString();
    final String orderType = "BID";
    final Ticker ticker = TickerFaker.createValid();
    TradeableItem tradeableItem = TradeableItemFaker.createValid();
    final BigDecimal orderAmount = new BigDecimal("86.75309");
    Currency currency = CurrencyFaker.createValid();
    final Date orderTimestamp = DateUtils.nowUtc().toDate();

    final List<OrderReadModel> expectedOrders = Lists.newArrayList();

    expectedOrders.add(new OrderReadModel(
      ObjectId.get().toString(),
      orderId,
      orderType,
      ticker.getSymbol(),
      tradeableItem.getSymbol(),
      orderAmount,
      currency.getSymbol(),
      orderTimestamp));

    when(readService.fetchOpenOrders(ticker.getSymbol())).thenReturn(expectedOrders);

    // Act
    OrderListReadModel actual = client().resource("/securities/" + ticker.getSymbol() + "/orders").get(OrderListReadModel.class);

    // Assert
    assertThat(actual.getOrders()).isEqualTo(expectedOrders);
    assertThat(actual.getTicker()).isEqualTo(ticker.getSymbol());
    verify(readService, times(1)).fetchOpenOrders(ticker.getSymbol());
  }


}
