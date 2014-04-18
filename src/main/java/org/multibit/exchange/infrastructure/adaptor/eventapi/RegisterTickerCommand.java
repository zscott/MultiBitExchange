package org.multibit.exchange.infrastructure.adaptor.eventapi;

import com.google.common.base.Preconditions;

/**
 * <p>Command used for registering a {@link org.multibit.exchange.domain.model.CurrencyPair}.</li>
 *
 * @since 0.0.1
 *  
 */
public class RegisterTickerCommand extends ExchangeCommand {

  private final String tickerSymbol;

  public RegisterTickerCommand(ExchangeId exchangeId, String tickerSymbol) {
    super(exchangeId);
    Preconditions.checkNotNull(tickerSymbol, "tickerSymbol must not be null");

    this.tickerSymbol = tickerSymbol;
  }

  public String getTickerSymbol() {
    return tickerSymbol;
  }
}
