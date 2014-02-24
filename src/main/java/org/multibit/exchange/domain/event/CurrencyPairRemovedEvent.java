package org.multibit.exchange.domain.event;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;
import org.multibit.exchange.domain.model.CurrencyPair;
import org.multibit.exchange.domain.model.ExchangeId;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * <p>Event used to indicate that a {@link CurrencyPair} was removed from an Exchange.</p>
 *
 * @since 0.0.1
 *         
 */
public class CurrencyPairRemovedEvent {

  @TargetAggregateIdentifier
  private final ExchangeId exchangeId;

  private final CurrencyPair currencyPair;

  // todo: refactor to collapse ticker, baseCurrency, and counterCurrency into CurrencyPair
  public CurrencyPairRemovedEvent(ExchangeId exchangeId, CurrencyPair currencyPair) {
    checkNotNull(exchangeId, "exchangeId must not be null");
    checkNotNull(currencyPair, "currencyPair must not be null");

    this.exchangeId = exchangeId;
    this.currencyPair = currencyPair;
  }

  public ExchangeId getExchangeId() {
    return exchangeId;
  }

  public CurrencyPair getCurrencyPair() {
    return currencyPair;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    CurrencyPairRemovedEvent that = (CurrencyPairRemovedEvent) o;

    if (!currencyPair.equals(that.currencyPair)) return false;
    if (!exchangeId.equals(that.exchangeId)) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = exchangeId.hashCode();
    result = 31 * result + currencyPair.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "CurrencyPairRegisteredEvent{" +
      "exchangeId=" + exchangeId +
      ", currencyPair=" + currencyPair +
      '}';
  }
}
