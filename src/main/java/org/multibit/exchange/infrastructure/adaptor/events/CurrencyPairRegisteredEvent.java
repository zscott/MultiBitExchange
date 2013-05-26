package org.multibit.exchange.infrastructure.adaptor.events;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;
import org.multibit.exchange.domainmodel.Currency;
import org.multibit.exchange.domainmodel.ExchangeId;
import org.multibit.exchange.domainmodel.Ticker;


import static com.google.common.base.Preconditions.checkNotNull;

/**
 * <p>Event used to indicate that a {@link org.multibit.exchange.domainmodel.CurrencyPair} was registered.</p>
 *
 * @since 0.0.1
 *         
 */
public class CurrencyPairRegisteredEvent {

  @TargetAggregateIdentifier
  private final ExchangeId exchangeId;

  private final Ticker ticker;

  private final Currency baseCurrency;

  private final Currency counterCurrency;

  // todo: refactor to collapse ticker, baseCurrency, and counterCurrency into CurrencyPair
  public CurrencyPairRegisteredEvent(ExchangeId exchangeId, Ticker ticker, Currency baseCurrency, Currency counterCurrency) {

    checkNotNull(exchangeId, "exchangeId must not be null");
    checkNotNull(ticker, "ticker must not be null");
    checkNotNull(baseCurrency, "baseCurrency must not be null");
    checkNotNull(counterCurrency, "counterCurrency must not be null");

    this.exchangeId = exchangeId;
    this.ticker = ticker;
    this.baseCurrency = baseCurrency;
    this.counterCurrency = counterCurrency;
  }

  public ExchangeId getExchangeId() {
    return exchangeId;
  }

  public String getTickerSymbol() {
    return ticker.getSymbol();
  }

  public Currency getBaseCurrency() {
    return baseCurrency;
  }

  public Currency getCounterCurrency() {
    return counterCurrency;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    CurrencyPairRegisteredEvent that = (CurrencyPairRegisteredEvent) o;

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
        ", baseCurrency='" + getBaseCurrency().getSymbol() + '\'' +
        ", counterCurrency='" + getCounterCurrency().getSymbol() + '\'' +
        '}';
  }

}
