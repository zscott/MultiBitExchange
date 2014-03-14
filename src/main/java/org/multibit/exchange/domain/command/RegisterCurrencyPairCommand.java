package org.multibit.exchange.domain.command;

import org.multibit.exchange.domain.model.CurrencyPair;
import org.multibit.exchange.domain.model.ExchangeId;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * <p>Command used for registering a {@link org.multibit.exchange.domain.model.CurrencyPair}.</li>
 *
 * @since 0.0.1
 *  
 */
public class RegisterCurrencyPairCommand extends ExchangeCommand {

  private final CurrencyPair currencyPair;

  public RegisterCurrencyPairCommand(ExchangeId exchangeId, CurrencyPair currencyPair) {
    super(exchangeId);
    checkNotNull(exchangeId, "exchangeId must not be null");
    checkNotNull(currencyPair, "currencyPair must not be null");

    this.currencyPair = currencyPair;
  }

  public CurrencyPair getCurrencyPair() {
    return currencyPair;
  }

  @Override
  public String toString() {
    return "RegisterCurrencyPairCommand{" +
        "exchangeId=" + exchangeId +
        ", currencyPair=" + currencyPair +
        '}';
  }
}
