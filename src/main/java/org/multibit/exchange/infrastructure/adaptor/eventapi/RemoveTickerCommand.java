package org.multibit.exchange.infrastructure.adaptor.eventapi;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * <p>Command used for removing a {@link org.multibit.exchange.domain.model.CurrencyPair}.</li>
 *
 * @since 0.0.1
 *  
 */
public class RemoveTickerCommand extends ExchangeCommand {

  private final String tickerSymbol;

  public RemoveTickerCommand(ExchangeId exchangeId, String tickerSymbol) {
    super(exchangeId);

    checkNotNull(tickerSymbol, "tickerSymbol must not be null");
    this.tickerSymbol = tickerSymbol;
  }

  public String getTickerSymbol() {
    return tickerSymbol;
  }

  @Override
  public String toString() {
    return "RemoveTickerCommand{" +
        "exchangeId=" + exchangeId +
        ", tickerSymbol=" + tickerSymbol +
        '}';
  }
}
