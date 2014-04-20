package org.multibit.exchange.domain.event;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;
import org.multibit.exchange.domain.model.CurrencyPair;
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

  private final String symbol;

  public CurrencyPairRemovedEvent(ExchangeId exchangeId, String symbol) {
    checkNotNull(exchangeId, "exchangeId must not be null");
    checkNotNull(symbol, "symbol must not be null");

    this.exchangeId = exchangeId;
    this.symbol = symbol;
  }

  public ExchangeId getExchangeId() {
    return exchangeId;
  }

  public String getSymbol() {
    return symbol;
  }

  @Override
  public String toString() {
    return "CurrencyPairRegisteredEvent{" +
        "exchangeId=" + exchangeId +
        ", symbol=" + symbol +
        '}';
  }
}
