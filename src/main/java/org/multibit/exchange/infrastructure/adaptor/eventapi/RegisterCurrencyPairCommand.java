package org.multibit.exchange.infrastructure.adaptor.eventapi;

import com.google.common.base.Preconditions;

/**
 * <p>Command used for registering a {@link org.multibit.exchange.domain.model.CurrencyPair}.</li>
 *
 * @since 0.0.1
 *  
 */
public class RegisterCurrencyPairCommand extends ExchangeCommand {

  private final String symbol;
  private final String baseCurrency;
  private final String counterCurrency;

  private RegisterCurrencyPairCommand(ExchangeId exchangeId, CurrencyPairDescriptor currencyPairDescriptor) {
    this(exchangeId, currencyPairDescriptor.getSymbol(), currencyPairDescriptor.getBaseCurrency(), currencyPairDescriptor.getCounterCurrency());
  }

  public RegisterCurrencyPairCommand(ExchangeId exchangeId, String symbol, String baseCurrency, String counterCurrency) {
    super(exchangeId);
    Preconditions.checkNotNull(symbol, "symbol must not be null");
    Preconditions.checkNotNull(baseCurrency, "baseCurrency must not be null");
    Preconditions.checkNotNull(counterCurrency, "counterCurrency must not be null");

    this.symbol = symbol;
    this.baseCurrency = baseCurrency;
    this.counterCurrency = counterCurrency;
  }

  public static RegisterCurrencyPairCommand create(ExchangeId exchangeId, CurrencyPairDescriptor currencyPairDescriptor) {
    Preconditions.checkNotNull(currencyPairDescriptor, "currencyPairDescriptor must not be null");
    return new RegisterCurrencyPairCommand(exchangeId, currencyPairDescriptor);
  }

  public String getSymbol() {
    return symbol;
  }

  public String getBaseCurrency() {
    return baseCurrency;
  }

  public String getCounterCurrency() {
    return counterCurrency;
  }
}
