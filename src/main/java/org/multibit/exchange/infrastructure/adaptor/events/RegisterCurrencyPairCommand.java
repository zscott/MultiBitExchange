package org.multibit.exchange.infrastructure.adaptor.events;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;
import org.multibit.exchange.domainmodel.Currency;
import org.multibit.exchange.domainmodel.CurrencyPair;
import org.multibit.exchange.domainmodel.ExchangeId;
import org.multibit.exchange.domainmodel.Ticker;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * <p>Command used for registering a {@link org.multibit.exchange.domainmodel.CurrencyPair}.</li>
 *
 * @since 0.0.1
 *         
 */
public class RegisterCurrencyPairCommand {

  @TargetAggregateIdentifier
  private final ExchangeId exchangeId;

  private final CurrencyPair currencyPair;

  public RegisterCurrencyPairCommand(ExchangeId exchangeId, Ticker ticker, Currency tradeableItem, Currency currency) {
    this(exchangeId, new CurrencyPair(tradeableItem, currency));
  }

  public RegisterCurrencyPairCommand(ExchangeId exchangeId, CurrencyPair currencyPair) {
    checkNotNull(exchangeId, "exchangeId must not be null");
    checkNotNull(currencyPair, "currencyPair must not be null");

    this.exchangeId = exchangeId;
    this.currencyPair = currencyPair;
  }

  public ExchangeId getExchangeId() {
    return exchangeId;
  }

  public CurrencyPair getCurrencyPair() {
    return currencyPair;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    RegisterCurrencyPairCommand that = (RegisterCurrencyPairCommand) o;

    if (!currencyPair.equals(that.currencyPair)) return false;
    if (!exchangeId.equals(that.exchangeId)) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = exchangeId.hashCode();
    result = 31 * result + currencyPair.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "RegisterCurrencyPairCommand{" +
      "exchangeId=" + exchangeId +
      ", currencyPair=" + currencyPair +
      '}';
  }
}
