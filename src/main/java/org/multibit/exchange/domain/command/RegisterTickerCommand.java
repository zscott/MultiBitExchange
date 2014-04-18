package org.multibit.exchange.domain.command;

import com.google.common.base.Preconditions;

/**
 * <p>Command used for registering a {@link org.multibit.exchange.domain.model.CurrencyPair}.</li>
 *
 * @since 0.0.1
 *  
 */
public class RegisterTickerCommand extends ExchangeCommand {

  private String tickerSymbol;

  public RegisterTickerCommand(ExchangeId exchangeId, CurrencyPairDescriptor currencyPair) {
    super(exchangeId);
    Preconditions.checkNotNull(currencyPair, "currencyPair must not be null");

    setTickerSymbol(currencyPair.getTickerSymbol());
  }

  private void setTickerSymbol(String tickerSymbol) {
    this.tickerSymbol = tickerSymbol;
  }

  public String getTickerSymbol() {
    return tickerSymbol;
  }
}
