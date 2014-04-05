package org.multibit.exchange.infrastructure.adaptor.web.restapi.resources;

import org.junit.Test;
import org.multibit.exchange.domain.command.ExchangeId;
import org.multibit.exchange.domain.model.MarketOrder;
import org.multibit.exchange.domain.model.Side;


public class ExchangeResourceTest extends BaseResourceTest {

  @Test
  public void testTestPlaceOrder_TickerBecomesUppercase() {
    // Arrange
    Side expectedSide = Side.SELL;
    String expectedTicker = "TICKER";

    ExchangeId exchangeId = fixture.getExchangeId();
    String broker = "broker";
    String side = "Sell";
    String qty = "10.005";
    String mixedTicker = "TiCkEr";
    String price = MarketOrder.MARKET_PRICE;

    OrderDescriptor orderDescriptor = new OrderDescriptor(broker, side, qty, mixedTicker, price);

    // Act
    exchangeResource.placeOrder(exchangeId.getCode(), orderDescriptor);

    // Assert
    assertPlaceOrderCalledOnExchangeService(broker, qty, expectedTicker, expectedSide);
  }

  @Test
  public void testTestPlaceOrder_CurrencyPairTickerBecomesUppercase() {
    // Arrange
    Side expectedSide = Side.SELL;
    String expectedTicker = "BASECCY/COUNTERCCY";

    ExchangeId exchangeId = fixture.getExchangeId();
    String broker = "broker";
    String side = "Sell";
    String qty = "1115.005";
    String mixedTicker = "BaseCCY/CounterCCY";
    String price = MarketOrder.MARKET_PRICE;

    OrderDescriptor orderDescriptor = new OrderDescriptor(broker, side, qty, mixedTicker, price);

    // Act
    exchangeResource.placeOrder(exchangeId.getCode(), orderDescriptor);

    // Assert
    assertPlaceOrderCalledOnExchangeService(broker, qty, expectedTicker, expectedSide);
  }


  @Test
  public void testPlaceMarketSellOrder() {
    // Arrange
    ExchangeId exchangeId = fixture.getExchangeId();
    String broker = "broker";
    String side = "Sell";
    String qty = "10.005";
    String ticker = "TICKER";
    String price = MarketOrder.MARKET_PRICE;

    Side expectedSide = Side.SELL;
    OrderDescriptor orderDescriptor = new OrderDescriptor(broker, side, qty, ticker, price);

    // Act
    exchangeResource.placeOrder(exchangeId.getCode(), orderDescriptor);

    // Assert
    assertPlaceOrderCalledOnExchangeService(broker, qty, ticker, expectedSide);
  }
}
