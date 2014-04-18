package org.multibit.exchange.infrastructure.adaptor.web.integration;

import org.junit.Ignore;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.multibit.exchange.domain.command.ExchangeId;
import org.multibit.exchange.domain.command.OrderDescriptor;
import org.multibit.exchange.domain.command.OrderId;
import org.multibit.exchange.testing.BrokerFaker;

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
    String expectedTicker = "DOGE/LTC";
    String expectedBroker = BrokerFaker.createValid();
    String expectedSide = "Buy";
    String expectedQty = "10.0";
    String expectedPrice = "500.27885";
    OrderDescriptor buyOrder = new OrderDescriptor(expectedBroker, expectedSide, expectedQty, expectedTicker, expectedPrice);

    // Act
    client()
        .resource("/exchanges/" + exchangeId.getIdentifier() + "/orders")
        .type(MediaType.APPLICATION_JSON)
        .post(buyOrder);

    // Assert
    ArgumentCaptor<OrderDescriptor> orderCaptor = ArgumentCaptor.forClass(OrderDescriptor.class);
    verify(exchangeService, times(1)).placeOrder(exchangeId, new OrderId(), orderCaptor.capture());

    OrderDescriptor capturedOrder = orderCaptor.getValue();
    assertThat(capturedOrder.getTicker()).isEqualTo(expectedTicker);
    assertThat(capturedOrder.getBroker()).isEqualTo(expectedBroker);
    assertThat(capturedOrder.getSide()).isEqualTo(expectedSide);
    assertThat(capturedOrder.getQty()).isEqualTo(expectedQty);
    assertThat(capturedOrder.getPrice()).isEqualTo(expectedPrice);
  }
}
