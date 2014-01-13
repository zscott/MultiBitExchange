package org.multibit.exchange.infrastructure.adaptor.api.resources;

import org.junit.Test;
import org.multibit.exchange.domainmodel.ExchangeId;


public class ExchangeResourceTest extends BaseResourceTest {

  @Test
  public void testPlaceBuyOrder() {
    // Arrange
    ExchangeId exchangeId = fixture.getExchangeId();
    BuyOrderDescriptor buyOrderDescriptor = createValidBuyOrder();

    // Act
    exchangeResource.placeBuyOrder(exchangeId.getName(), buyOrderDescriptor);

    // Assert
    assertPlaceBuyOrderCalled(exchangeService, buyOrderDescriptor);
  }

}
