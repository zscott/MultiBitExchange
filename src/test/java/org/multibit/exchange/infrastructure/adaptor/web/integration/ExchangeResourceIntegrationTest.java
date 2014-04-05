package org.multibit.exchange.infrastructure.adaptor.web.integration;

import org.junit.Ignore;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.multibit.exchange.domain.command.ExchangeId;
import org.multibit.exchange.domain.model.LimitOrder;
import org.multibit.exchange.domain.model.Side;
import org.multibit.exchange.domain.model.Ticker;
import org.multibit.exchange.infrastructure.adaptor.web.restapi.resources.OrderDescriptor;
import org.multibit.exchange.testing.BrokerFaker;
import org.multibit.exchange.testing.TickerFaker;

import javax.ws.rs.core.MediaType;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


public class ExchangeResourceIntegrationTest extends BaseDropWizardResourceIntegrationTest {

  private ExchangeId exchangeId = new ExchangeId();

  @Test
  @Ignore("See ISSUE #47")
  public void POST_BuyOrder() {
    // Arrange
    Ticker expectedTicker = TickerFaker.createValid();
    String expectedBroker = BrokerFaker.createValid();
    String side = "Buy";
    String qty = "10.0";
    String expectedLimitPrice = "500.27885";
    OrderDescriptor buyOrder = new OrderDescriptor(expectedBroker, side, qty, expectedTicker.getSymbol(), expectedLimitPrice);

    // Act
    client()
        .resource("/exchanges/" + exchangeId.getCode() + "/orders")
        .type(MediaType.APPLICATION_JSON)
        .post(buyOrder);

    // Assert
    ArgumentCaptor<LimitOrder> order = ArgumentCaptor.forClass(LimitOrder.class);
    verify(exchangeService, times(1)).placeOrder(exchangeId, order.capture());

    assertThat(order.getValue().getTicker()).isEqualTo(expectedTicker);
    assertThat(order.getValue().isLimitOrder()).isTrue();
    assertThat(order.getValue().getLimitPrice().getRaw()).isEqualTo(expectedLimitPrice);
    assertThat(order.getValue().getBroker()).isEqualTo(expectedBroker);
    assertThat(order.getValue().getInitialQuantity().getRaw()).isEqualTo(qty);
    assertThat(order.getValue().getSide()).isEqualTo(Side.BUY);
  }

}
