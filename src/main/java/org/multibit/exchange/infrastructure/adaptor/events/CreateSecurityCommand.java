package org.multibit.exchange.infrastructure.adaptor.events;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;
import org.multibit.exchange.domainmodel.Currency;
import org.multibit.exchange.domainmodel.MarketId;
import org.multibit.exchange.domainmodel.Ticker;
import org.multibit.exchange.domainmodel.TradeableItem;

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
  private final MarketId marketId;

  private final Ticker ticker;

  private final Currency currency;

  private final TradeableItem tradeableItem;

  public CreateSecurityCommand(MarketId marketId, Ticker ticker, TradeableItem tradeableItem, Currency currency) {

    checkNotNull(marketId, "securityId must not be null");
    checkNotNull(ticker, "ticker must not be null");
    checkNotNull(tradeableItem, "tradeableItem must not be null");
    checkNotNull(currency, "currency must not be null");

    this.marketId = marketId;
    this.ticker = ticker;
    this.tradeableItem = tradeableItem;
    this.currency = currency;
  }

  public MarketId getMarketId() {
    return marketId;
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
      "id='" + marketId + '\'' +
      ", tickerSymbol='" + getTickerSymbol() + '\'' +
      ", tradeableItemSymbol='" + getTradeableItemSymbol() + '\'' +
      ", currencySymbol='" + getCurrencySymbol() + '\'' +
      '}';
  }

}
