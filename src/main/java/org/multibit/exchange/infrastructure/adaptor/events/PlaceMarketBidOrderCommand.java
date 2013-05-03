package org.multibit.exchange.infrastructure.adaptor.events;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;
import org.multibit.exchange.domainmodel.MarketId;
import org.multibit.exchange.domainmodel.TradeableItemQuantity;

/**
 * <p>Command to provide the following to the application:</p>
 * <ul>
 * <li>An event-based mechanism for placing a market bid order.</li>
 * </ul>
 *
 * @since 0.0.1
 */
public class PlaceMarketBidOrderCommand {

  @TargetAggregateIdentifier
  private final MarketId marketId;

  private final TradeableItemQuantity quantity;

  public PlaceMarketBidOrderCommand(MarketId marketId, TradeableItemQuantity quantity) {
    this.marketId = marketId;
    this.quantity = quantity;
  }

  public MarketId getMarketId() {
    return marketId;
  }

  public TradeableItemQuantity getQuantity() {
    return quantity;
  }
}
