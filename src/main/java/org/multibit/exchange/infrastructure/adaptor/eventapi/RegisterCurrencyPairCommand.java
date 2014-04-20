package org.multibit.exchange.infrastructure.adaptor.eventapi;

import com.google.common.base.Preconditions;

/**
 * <p>Command used for registering a {@link org.multibit.exchange.domain.model.CurrencyPair}.</li>
 *
 * @since 0.0.1
 *  
 */
public class RegisterCurrencyPairCommand extends ExchangeCommand {

  private final CurrencyPairId currencyPairId;
  private final String baseCurrency;
  private final String counterCurrency;

  public RegisterCurrencyPairCommand(ExchangeId exchangeId, CurrencyPairId currencyPairId, String baseCurrency, String counterCurrency) {
    super(exchangeId);
    Preconditions.checkNotNull(currencyPairId, "currencyPairId must not be null");
    Preconditions.checkNotNull(baseCurrency, "baseCurrency must not be null");
    Preconditions.checkNotNull(counterCurrency, "counterCurrency must not be null");

    this.currencyPairId = currencyPairId;
    this.baseCurrency = baseCurrency;
    this.counterCurrency = counterCurrency;
  }

  public CurrencyPairId getCurrencyPairId() {
    return currencyPairId;
  }

  public String getBaseCurrency() {
    return baseCurrency;
  }

  public String getCounterCurrency() {
    return counterCurrency;
  }
}
