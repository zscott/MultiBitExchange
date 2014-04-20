package org.multibit.exchange.infrastructure.adaptor.eventapi;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * <p>Command used for removing a {@link org.multibit.exchange.domain.model.CurrencyPair}.</li>
 *
 * @since 0.0.1
 *  
 */
public class RemoveCurrencyPairCommand extends ExchangeCommand {

  private final CurrencyPairId currencyPairId;

  public RemoveCurrencyPairCommand(ExchangeId exchangeId, CurrencyPairId currencyPairId) {
    super(exchangeId);

    checkNotNull(currencyPairId, "tickerSymbol must not be null");
    this.currencyPairId = currencyPairId;
  }

  public CurrencyPairId getCurrencyPairId() {
    return currencyPairId;
  }
}
