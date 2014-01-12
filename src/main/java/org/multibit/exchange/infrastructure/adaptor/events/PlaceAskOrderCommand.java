package org.multibit.exchange.infrastructure.adaptor.events;

/**
 * Created by scott on 1/12/14.
 */

/**
 * <p>Command used for placing a {@link AskOrder}</li>
 *
 * @since 0.0.1
 */
public class PlaceAskOrderCommand {

    @TargetAggregateIdentifier
    private final ExchangeId exchangeId;

    private final TradeableItemQuantity quantity;

    public PlaceAskOrderCommand(ExchangeId exchangeId, TradeableItemQuantity quantity) {
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
