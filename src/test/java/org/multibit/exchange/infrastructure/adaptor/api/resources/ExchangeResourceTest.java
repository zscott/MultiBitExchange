package org.multibit.exchange.infrastructure.adaptor.api.resources;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.multibit.exchange.domainmodel.ExchangeId;
import org.multibit.exchange.domainmodel.MarketOrder;
import org.multibit.exchange.domainmodel.SecurityOrder;
import org.multibit.exchange.domainmodel.Side;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


public class ExchangeResourceTest extends BaseResourceTest {

  @Test
  public void testPlaceMarketSellOrder() {
    // Arrange
    ExchangeId exchangeId = fixture.getExchangeId();
    String broker = "broker";
    String side = "Sell";
    String qty = "10.005";
    String ticker = "TICKER";
    String price = "M";

    Side expectedSide = Side.SELL;
    OrderDescriptor orderDescriptor = new OrderDescriptor(broker, side, qty, ticker, price);

    // Act
    exchangeResource.placeOrder(exchangeId.getName(), orderDescriptor);

    // Assert
    ArgumentCaptor<ExchangeId> exchangeIdCaptor = ArgumentCaptor.forClass(ExchangeId.class);
    ArgumentCaptor<SecurityOrder> orderCaptor = ArgumentCaptor.forClass(SecurityOrder.class);
    verify(exchangeService, times(1)).placeOrder(exchangeIdCaptor.capture(), orderCaptor.capture());

    assertEquals(fixture.getExchangeId(), exchangeIdCaptor.getValue());

    MarketOrder actualOrder = (MarketOrder) orderCaptor.getValue();
    assertEquals(broker, actualOrder.getBroker());
    assertEquals(expectedSide, actualOrder.getSide());
    assertEquals(qty, actualOrder.getQuantity().getRaw());
    assertEquals(ticker, actualOrder.getTicker().getSymbol());
  }

}
