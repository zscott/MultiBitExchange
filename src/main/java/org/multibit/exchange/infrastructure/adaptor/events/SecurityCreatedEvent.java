package org.multibit.exchange.infrastructure.adaptor.events;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;
import org.multibit.exchange.domainmodel.Currency;
import org.multibit.exchange.domainmodel.MarketId;
import org.multibit.exchange.domainmodel.Ticker;
import org.multibit.exchange.domainmodel.TradeableItem;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * <p>Command to provide the following to the application:</p>
 * <ul>
 * <li>an event driven mechanism for creating a new security</li>
 * </ul>
 *
 * @since 0.0.1
 *         
 */
public class SecurityCreatedEvent {

  @TargetAggregateIdentifier
  private final MarketId marketId;

  private final Ticker ticker;

  private final TradeableItem tradeableItem;

  private final Currency currency;

  public SecurityCreatedEvent(MarketId marketId, Ticker ticker, TradeableItem tradeableItem, Currency currency) {

    checkNotNull(marketId, "marketId must not be null");
    checkNotNull(ticker, "ticker must not be null");
    checkNotNull(tradeableItem, "tradeableItem must not be null");
    checkNotNull(currency, "currency must not be null");

    this.marketId = marketId;
    this.ticker = ticker;
    this.tradeableItem = tradeableItem;
    this.currency = currency;
  }

  public MarketId getMarketId() {
    return marketId;
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

    if (!marketId.equals(that.marketId)) return false;

    return true;
  }

  @Override
  public int hashCode() {
    return marketId.hashCode();
  }

  @Override
  public String toString() {
    return "SecurityCreatedEvent{" +
        "id='" + marketId + '\'' +
        ", tickerSymbol='" + getTickerSymbol() + '\'' +
        ", tradeableItemSymbol='" + getTradeableItemSymbol() + '\'' +
        ", currencySymbol='" + getCurrencySymbol() + '\'' +
        '}';
  }
}
