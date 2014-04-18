package org.multibit.exchange.domain.event;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;
import org.multibit.exchange.domain.model.CurrencyPair;
import org.multibit.exchange.domain.model.Ticker;
import org.multibit.exchange.infrastructure.adaptor.eventapi.ExchangeId;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * <p>Event used to indicate that a {@link CurrencyPair} was registered with an Exchange.</p>
 *
 * @since 0.0.1
 *  
 */
public class TickerRegisteredEvent {

  @TargetAggregateIdentifier
  private final ExchangeId exchangeId;

  private final Ticker ticker;

  public TickerRegisteredEvent(ExchangeId exchangeId, Ticker ticker) {
    checkNotNull(exchangeId, "exchangeId must not be null");
    checkNotNull(ticker, "currencyPair must not be null");

    this.exchangeId = exchangeId;
    this.ticker = ticker;
  }

  public ExchangeId getExchangeId() {
    return exchangeId;
  }

  public Ticker getTicker() {
    return ticker;
  }

  @Override
  public String toString() {
    return "TickerRegisteredEvent{" +
        "exchangeId=" + exchangeId +
        ", ticker=" + ticker +
        '}';
  }
}
