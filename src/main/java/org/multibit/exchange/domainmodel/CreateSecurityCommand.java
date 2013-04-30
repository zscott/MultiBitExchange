package org.multibit.exchange.domainmodel;

import com.google.common.base.Strings;
import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;


import static com.google.common.base.Preconditions.checkArgument;
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
  private final SecurityId id;

  private final Ticker ticker;

  private final String currencySymbol;

  private final TradeableItem tradeableItem;

  public CreateSecurityCommand(SecurityId securityId, Ticker ticker, TradeableItem tradeableItem, String currencySymbol) {

    checkNotNull(securityId, "securityId must not be null: '%s'", securityId);
    checkNotNull(ticker, "ticker must not be null: '%s'", ticker);
    checkArgument(!Strings.isNullOrEmpty(currencySymbol), "currencySymbol must not be null or empty: '%s'", currencySymbol);

    this.id = securityId;
    this.ticker = ticker;
    this.tradeableItem = tradeableItem;
    this.currencySymbol = currencySymbol;
  }

  public SecurityId getId() {
    return id;
  }

  public Ticker getTicker() {
    return ticker;
  }

  public String getTickerSymbol() {
    return ticker.getSymbol();
  }

  public String getTradeableItemSymbol() {
    return tradeableItem.getSymbol();
  }

  public String getCurrencySymbol() {
    return currencySymbol;
  }

  @Override
  public String toString() {
    return "CreateSecurityCommand{" +
      "id='" + id + '\'' +
      ", tickerSymbol='" + getTickerSymbol() + '\'' +
      ", tradeableItemSymbol='" + getTradeableItemSymbol() + '\'' +
      ", currencySymbol='" + getCurrencySymbol() + '\'' +
      '}';
  }
}
