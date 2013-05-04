package org.multibit.exchange.infrastructure.adaptor.api.resources;

import org.multibit.exchange.domainmodel.Currency;
import org.multibit.exchange.domainmodel.ExchangeId;
import org.multibit.exchange.domainmodel.OrderAmount;
import org.multibit.exchange.domainmodel.Ticker;
import org.multibit.exchange.domainmodel.TradeableItem;
import org.multibit.exchange.service.ExchangeService;
import org.multibit.exchange.testing.CurrencyFaker;
import org.multibit.exchange.testing.ExchangeIdFaker;
import org.multibit.exchange.testing.OrderAmountFaker;
import org.multibit.exchange.testing.TickerFaker;
import org.multibit.exchange.testing.TradeableItemFaker;


import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * <p>Fixture to provide the following to tests:</p>
 * <ul>
 * <li>Encapsulates creation of related objects for testing</li>
 * </ul>
 *
 * @since 0.0.1
 *         
 */
public class ResourceTestFixture {

  private ExchangeId exchangeId = ExchangeIdFaker.createValid();
  private Ticker ticker = TickerFaker.createValid();
  private TradeableItem tradeableItem = TradeableItemFaker.createValid();
  private Currency currency = CurrencyFaker.createValid();


  public ResourceTestFixture() {

  }

  SecurityDescriptor createValidSecurityDescriptor() {
    return new SecurityDescriptor(
        ticker.getSymbol(),
        tradeableItem.getSymbol(),
        currency.getSymbol());
  }

  Ticker getTicker() {
    return TickerFaker.createValid();
  }

  Currency getCurrency() {
    return CurrencyFaker.createValid();
  }

  TradeableItem getTradeableItem() {
    return TradeableItemFaker.createValid();
  }

  public ExchangeId getExchangeId() {
    return exchangeId;
  }

  BidOrderDescriptor createValidBidOrder() {
    return new BidOrderDescriptor(ticker.getSymbol(), OrderAmountFaker.createValid().getRaw());
  }

  void assertPlaceBidOrderCalled(ExchangeService exchangeService, BidOrderDescriptor bidOrderDescriptor) {
    Ticker ticker = new Ticker(bidOrderDescriptor.getTickerSymbol());
    OrderAmount orderAmount = new OrderAmount(bidOrderDescriptor.getOrderAmount());
    verify(exchangeService, times(1)).placeBidOrder(exchangeId, ticker, orderAmount);
  }

  void assertCreateSecurityCalled(ExchangeService service, SecurityDescriptor securityDescriptor) {
    Ticker ticker = new Ticker(securityDescriptor.getTickerSymbol());
    TradeableItem tradeableItem = new TradeableItem(securityDescriptor.getTradeableItemSymbol());
    Currency currency = new Currency(securityDescriptor.getCurrencySymbol());
    verify(service, times(1)).createSecurity(getExchangeId(), ticker, tradeableItem, currency);
  }
}
