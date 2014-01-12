package org.multibit.exchange.infrastructure.adaptor.events;

import org.multibit.exchange.domainmodel.ExchangeId;
import org.multibit.exchange.domainmodel.TradeableItemQuantity;

/**
 * Created by scott on 1/12/14.
 */
/**
 * <p>Event to indicate that a bid order was placed</p>
 *
 * @since 0.0.1
 */

public class AskOrderPlacedEvent {
    public AskOrderPlacedEvent(ExchangeId exchangeId, TradeableItemQuantity quantity) {
    }
}