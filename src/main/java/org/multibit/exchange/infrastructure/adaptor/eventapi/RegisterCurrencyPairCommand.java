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
  private final CurrencyId baseCurrencyId;
  private final CurrencyId counterCurrencyId;

  public RegisterCurrencyPairCommand(ExchangeId exchangeId, CurrencyPairId currencyPairId, CurrencyId baseCurrencyId, CurrencyId counterCurrencyId) {
    super(exchangeId);
    Preconditions.checkNotNull(currencyPairId, "currencyPairId must not be null");
    Preconditions.checkNotNull(baseCurrencyId, "baseCurrencyId must not be null");
    Preconditions.checkNotNull(counterCurrencyId, "counterCurrencyId must not be null");

    this.currencyPairId = currencyPairId;
    this.baseCurrencyId = baseCurrencyId;
    this.counterCurrencyId = counterCurrencyId;
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
