package org.multibit.exchange.infrastructure.adaptor.events;

import org.multibit.exchange.domain.model.ExchangeId;
import org.multibit.exchange.domain.model.TradeableItemQuantity;

/**
 * Created by scott on 1/12/14.
 */
/**
 * <p>Event to indicate that a sell order was placed</p>
 *
 * @since 0.0.1
 */

public class SellOrderPlacedEvent {
    public SellOrderPlacedEvent(ExchangeId exchangeId, TradeableItemQuantity quantity) {
    }
}