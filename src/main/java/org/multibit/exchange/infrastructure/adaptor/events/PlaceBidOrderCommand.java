package org.multibit.exchange.infrastructure.adaptor.events;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;
import org.multibit.exchange.domainmodel.ExchangeId;
import org.multibit.exchange.domainmodel.TradeableItemQuantity;

/**
 * <p>Command used for placing a {@link BidOrder}</li>
 *
 * @since 0.0.1
 */
public class PlaceBidOrderCommand {

  @TargetAggregateIdentifier
  private final ExchangeId exchangeId;

  private final TradeableItemQuantity quantity;

  public PlaceBidOrderCommand(ExchangeId exchangeId, TradeableItemQuantity quantity) {
    this.exchangeId = exchangeId;
    this.quantity = quantity;
  }

  public ExchangeId getExchangeId() {
    return exchangeId;
  }

  public TradeableItemQuantity getQuantity() {
    return quantity;
  }
}
