package org.multibit.exchange.infrastructure.adaptor.events;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;
import org.multibit.exchange.domainmodel.Currency;
import org.multibit.exchange.domainmodel.ExchangeId;
import org.multibit.exchange.domainmodel.Ticker;
import org.multibit.exchange.domainmodel.TradeableItem;


import static com.google.common.base.Preconditions.checkNotNull;

/**
 * <p>Event used to indicate that a {@link org.multibit.exchange.domainmodel.Security} was created</p>
 *
 * @since 0.0.1
 *         
 */
public class SecurityCreatedEvent {

  @TargetAggregateIdentifier
  private final ExchangeId exchangeId;

  private final Ticker ticker;

  private final TradeableItem tradeableItem;

  private final Currency currency;

  public SecurityCreatedEvent(ExchangeId exchangeId, Ticker ticker, TradeableItem tradeableItem, Currency currency) {

    checkNotNull(exchangeId, "exchangeId must not be null");
    checkNotNull(ticker, "ticker must not be null");
    checkNotNull(tradeableItem, "tradeableItem must not be null");
    checkNotNull(currency, "currency must not be null");

    this.exchangeId = exchangeId;
    this.ticker = ticker;
    this.tradeableItem = tradeableItem;
    this.currency = currency;
  }

  public ExchangeId getExchangeId() {
    return exchangeId;
  }

  public String getTickerSymbol() {
    return ticker.getSymbol();
  }

  public String getTradeableItemSymbol() {
    return tradeableItem.getSymbol();
  }

  public String getCurrencySymbol() {
    return currency.getSymbol();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    SecurityCreatedEvent that = (SecurityCreatedEvent) o;

    if (!exchangeId.equals(that.exchangeId)) return false;

    return true;
  }

  @Override
  public int hashCode() {
    return exchangeId.hashCode();
  }

  @Override
  public String toString() {
    return "SecurityCreatedEvent{" +
        "id='" + exchangeId + '\'' +
        ", tickerSymbol='" + getTickerSymbol() + '\'' +
        ", tradeableItemSymbol='" + getTradeableItemSymbol() + '\'' +
        ", currencySymbol='" + getCurrencySymbol() + '\'' +
        '}';
  }
}
