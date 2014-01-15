package org.multibit.exchange.infrastructure.adaptor.events;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;
import org.multibit.exchange.domainmodel.ExchangeId;
import org.multibit.exchange.domainmodel.TradeableItemQuantity;


/**
 * Created by scott on 1/12/14.
 */

/**
 * <p>Command used for placing a {@link SellOrder}</li>
 *
 * @since 0.0.1
 */
public class PlaceSellOrderCommand {

    @TargetAggregateIdentifier
    private final ExchangeId exchangeId;

    private final TradeableItemQuantity quantity;

    public PlaceSellOrderCommand(ExchangeId exchangeId, TradeableItemQuantity quantity) {
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
