package org.multibit.exchange.domain.event;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;
import org.multibit.exchange.domain.model.CurrencyPair;
import org.multibit.exchange.infrastructure.adaptor.eventapi.CurrencyId;
import org.multibit.exchange.infrastructure.adaptor.eventapi.CurrencyPairId;
import org.multibit.exchange.infrastructure.adaptor.eventapi.ExchangeId;

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
  private final CurrencyPairId currencyPairId;
  private final CurrencyId baseCurrencyId;
  private final CurrencyId counterCurrencyId;

  public CurrencyPairRegisteredEvent(ExchangeId exchangeId, CurrencyPairId currencyPairId, CurrencyId baseCurrencyId, CurrencyId counterCurrencyId) {
    checkNotNull(exchangeId, "exchangeId must not be null");
    checkNotNull(currencyPairId, "currencyPairId must not be null");
    checkNotNull(baseCurrencyId, "baseCurrencyId must not be null");
    checkNotNull(counterCurrencyId, "counterCurrencyId must not be null");

    this.exchangeId = exchangeId;
    this.currencyPairId = currencyPairId;
    this.baseCurrencyId = baseCurrencyId;
    this.counterCurrencyId = counterCurrencyId;
  }

  public ExchangeId getExchangeId() {
    return exchangeId;
  }

  public CurrencyPairId getCurrencyPairId() {
    return currencyPairId;
  }

  public CurrencyId getBaseCurrencyId() {
    return baseCurrencyId;
  }

  public CurrencyId getCounterCurrencyId() {
    return counterCurrencyId;
  }
}
