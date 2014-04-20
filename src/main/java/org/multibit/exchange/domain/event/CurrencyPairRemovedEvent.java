package org.multibit.exchange.domain.event;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;
import org.multibit.exchange.domain.model.CurrencyPair;
import org.multibit.exchange.infrastructure.adaptor.eventapi.CurrencyPairId;
import org.multibit.exchange.infrastructure.adaptor.eventapi.ExchangeId;

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

  private final CurrencyPairId currencyPairId;

  public CurrencyPairRemovedEvent(ExchangeId exchangeId, CurrencyPairId currencyPairId) {
    checkNotNull(exchangeId, "exchangeId must not be null");
    checkNotNull(currencyPairId, "symbol must not be null");

    this.exchangeId = exchangeId;
    this.currencyPairId = currencyPairId;
  }

  public ExchangeId getExchangeId() {
    return exchangeId;
  }

  public CurrencyPairId getCurrencyPairId() {
    return currencyPairId;
  }
}
