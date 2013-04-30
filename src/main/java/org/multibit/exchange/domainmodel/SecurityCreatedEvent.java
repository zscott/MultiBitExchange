package org.multibit.exchange.domainmodel;

import com.google.common.base.Strings;
import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;


import static com.google.common.base.Preconditions.checkArgument;
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
  private final SecurityId id;

  private final Ticker ticker;

  private final String tradeableItemSymbol;

  private final String currencySymbol;

  public SecurityCreatedEvent(SecurityId id, Ticker ticker, String tradeableItemSymbol, String currencySymbol) {

    checkNotNull(id, "id must not be null");
    checkNotNull(ticker, "ticker must not be null");
    checkArgument(!Strings.isNullOrEmpty(tradeableItemSymbol), "tradeableItemSymbol must not be null or empty: '%s'", tradeableItemSymbol);
    checkArgument(!Strings.isNullOrEmpty(currencySymbol), "currencySymbol must not be null or empty: '%s'", currencySymbol);

    this.id = id;
    this.ticker = ticker;
    this.tradeableItemSymbol = tradeableItemSymbol;
    this.currencySymbol = currencySymbol;
  }

  public SecurityId getId() {
    return id;
  }

  public String getTickerSymbol() {
    return ticker.getSymbol();
  }

  public String getTradeableItemSymbol() {
    return tradeableItemSymbol;
  }

  public String getCurrencySymbol() {
    return currencySymbol;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    SecurityCreatedEvent that = (SecurityCreatedEvent) o;

    if (!id.equals(that.id)) return false;

    return true;
  }

  @Override
  public int hashCode() {
    return id.hashCode();
  }

  @Override
  public String toString() {
    return "SecurityCreatedEvent{" +
        "id='" + id + '\'' +
        ", tickerSymbol='" + getTickerSymbol() + '\'' +
        ", tradeableItemSymbol='" + tradeableItemSymbol + '\'' +
        ", currencySymbol='" + currencySymbol + '\'' +
        '}';
  }
}
