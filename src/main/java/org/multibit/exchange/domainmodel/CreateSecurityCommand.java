package org.multibit.exchange.domainmodel;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * <p>Command to provide the following to the application:</p>
 * <ul>
 * <li>an event driven mechanism for creating a new security</li>
 * </ul>
 *
 * @since 0.0.1
 *         
 */
public class CreateSecurityCommand {

  @TargetAggregateIdentifier
  private final SecurityId securityId;

  private final Ticker ticker;

  private final Currency currency;

  private final TradeableItem tradeableItem;

  public CreateSecurityCommand(SecurityId securityId, Ticker ticker, TradeableItem tradeableItem, Currency currency) {

    checkNotNull(securityId, "securityId must not be null");
    checkNotNull(ticker, "ticker must not be null");
    checkNotNull(tradeableItem, "tradeableItem must not be null");
    checkNotNull(currency, "currency must not be null");

    this.securityId = securityId;
    this.ticker = ticker;
    this.tradeableItem = tradeableItem;
    this.currency = currency;
  }

  public SecurityId getSecurityId() {
    return securityId;
  }

  public Ticker getTicker() {
    return ticker;
  }

  public String getTickerSymbol() {
    return ticker.getSymbol();
  }

  public TradeableItem getTradeableItem() {
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
  public String toString() {
    return "CreateSecurityCommand{" +
      "id='" + securityId + '\'' +
      ", tickerSymbol='" + getTickerSymbol() + '\'' +
      ", tradeableItemSymbol='" + getTradeableItemSymbol() + '\'' +
      ", currencySymbol='" + getCurrencySymbol() + '\'' +
      '}';
  }

}
