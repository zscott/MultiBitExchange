package org.multibit.exchange.domain.event;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;
import org.multibit.exchange.domain.command.ExchangeId;
import org.multibit.exchange.domain.model.CurrencyPair;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * <p>Event used to indicate that a {@link CurrencyPair} was registered with an Exchange.</p>
 *
 * @since 0.0.1
 *  
 */
public class CurrencyPairRegisteredEvent {

  @TargetAggregateIdentifier
  private final ExchangeId exchangeId;

  private final CurrencyPair currencyPair;

  // todo: refactor to collapse ticker, baseCurrency, and counterCurrency into CurrencyPair
  public CurrencyPairRegisteredEvent(ExchangeId exchangeId, CurrencyPair currencyPair) {
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
  public String toString() {
    return "CurrencyPairRegisteredEvent{" +
        "exchangeId=" + exchangeId +
        ", currencyPair=" + currencyPair +
        '}';
  }
}
