package org.multibit.exchange.infrastructure.adaptor.api.resources;

import org.junit.Test;
import org.multibit.exchange.domainmodel.ExchangeId;


public class ExchangeResourceTest extends BaseResourceTest {

  @Test
  public void testPlaceBidOrder() {
    // Arrange
    ExchangeId exchangeId = fixture.getExchangeId();
    BidOrderDescriptor bidOrderDescriptor = createValidBidOrder();

    // Act
    exchangeResource.placeBidOrder(exchangeId.getName(), bidOrderDescriptor);

    // Assert
    assertPlaceBidOrderCalled(exchangeService, bidOrderDescriptor);
  }

}
