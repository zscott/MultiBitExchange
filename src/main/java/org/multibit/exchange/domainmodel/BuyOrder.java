package org.multibit.exchange.domainmodel;

import com.google.common.base.Optional;
import org.joda.time.DateTime;

/**
 * <p>A bid order</p>
 *
 * @since 0.0.1
 *         
 */
public class BuyOrder extends SecurityOrder {

  public BuyOrder(SecurityOrderId id, OrderType orderTypeSpec, CurrencyPair currencyPair, ItemQuantity quantity, DateTime createdTime) {
    super(id, orderTypeSpec, currencyPair, quantity, createdTime);
  }

  @Override
  public Optional<Trade> addToOrderbookAndExecuteTrade(OrderBook orderBook) throws DuplicateOrderException {
    return orderBook.addBidOrderAndMatchAsks(this);
  }

  protected String getBuyOrSellString() {
    return "Buy";
  }
}
