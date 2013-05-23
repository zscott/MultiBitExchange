package org.multibit.exchange.infrastructure.adaptor.events;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;
import org.multibit.exchange.domainmodel.Currency;
import org.multibit.exchange.domainmodel.ExchangeId;
import org.multibit.exchange.domainmodel.Ticker;
import org.multibit.exchange.domainmodel.TradeableItem;


import static com.google.common.base.Preconditions.checkNotNull;

/**
 * <p>Command used for creating a new {@link org.multibit.exchange.domainmodel.Security}</li>
 *
 * @since 0.0.1
 *         
 */
public class CreateSecurityCommand {

  @TargetAggregateIdentifier
  private final ExchangeId exchangeId;

  private final Ticker ticker;

  private final Currency currency;

  private final Currency tradeableItem;

  public CreateSecurityCommand(ExchangeId exchangeId, Ticker ticker, Currency tradeableItem, Currency currency) {

    checkNotNull(exchangeId, "securityId must not be null");
    checkNotNull(ticker, "ticker must not be null");
    checkNotNull(tradeableItem, "tradeableItem must not be null");
    checkNotNull(currency, "currency must not be null");

    this.exchangeId = exchangeId;
    this.ticker = ticker;
    this.tradeableItem = tradeableItem;
    this.currency = currency;
  }

  public ExchangeId getExchangeId() {
    return exchangeId;
  }

  public Ticker getTicker() {
    return ticker;
  }

  public String getTickerSymbol() {
    return ticker.getSymbol();
  }

  public Currency getTradeableItem() {
    return tradeableItem;
  }

  public String getTradeableItemSymbol() {
    return tradeableItem.getSymbol();
  }

  public Currency getCurrency() {
    return currency;
  }

  public String getCurrencySymbol() {
    return currency.getSymbol();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    CreateSecurityCommand that = (CreateSecurityCommand) o;

    if (!currency.equals(that.currency)) return false;
    if (!exchangeId.equals(that.exchangeId)) return false;
    if (!ticker.equals(that.ticker)) return false;
    if (!tradeableItem.equals(that.tradeableItem)) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = exchangeId.hashCode();
    result = 31 * result + ticker.hashCode();
    result = 31 * result + currency.hashCode();
    result = 31 * result + tradeableItem.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "CreateSecurityCommand{" +
        "id='" + exchangeId + '\'' +
        ", tickerSymbol='" + getTickerSymbol() + '\'' +
        ", tradeableItemSymbol='" + getTradeableItemSymbol() + '\'' +
        ", currencySymbol='" + getCurrencySymbol() + '\'' +
        '}';
  }

}
