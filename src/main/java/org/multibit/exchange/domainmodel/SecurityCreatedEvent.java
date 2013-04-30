package org.multibit.exchange.domainmodel;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

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
  private final SecurityId securityId;

  private final Ticker ticker;

  private final TradeableItem tradeableItem;

  private final Currency currency;

  public SecurityCreatedEvent(SecurityId securityId, Ticker ticker, TradeableItem tradeableItem, Currency currency) {

    checkNotNull(securityId, "securityId must not be null");
    checkNotNull(ticker, "ticker must not be null");
    checkNotNull(tradeableItem, "tradeableItem must not be null");
    checkNotNull(currency, "currency must not be null");

    this.securityId = securityId;
    this.ticker = ticker;
    this.tradeableItem = tradeableItem;
    this.currency = currency;
  }

  public SecurityId getSecurityId() {
    return securityId;
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

    if (!securityId.equals(that.securityId)) return false;

    return true;
  }

  @Override
  public int hashCode() {
    return securityId.hashCode();
  }

  @Override
  public String toString() {
    return "SecurityCreatedEvent{" +
        "id='" + securityId + '\'' +
        ", tickerSymbol='" + getTickerSymbol() + '\'' +
        ", tradeableItemSymbol='" + getTradeableItemSymbol() + '\'' +
        ", currencySymbol='" + getCurrencySymbol() + '\'' +
        '}';
  }
}
