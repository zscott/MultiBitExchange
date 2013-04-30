package org.multibit.exchange.domainmodel;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

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
  private final SecurityId securityId;

  private final TradeableItemQuantity quantity;

  public PlaceMarketBidOrderCommand(SecurityId securityId, TradeableItemQuantity quantity) {
    this.securityId = securityId;
    this.quantity = quantity;
  }

  public SecurityId getSecurityId() {
    return securityId;
  }

  public TradeableItemQuantity getQuantity() {
    return quantity;
  }
}
