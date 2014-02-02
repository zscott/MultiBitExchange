package org.multibit.exchange.domain.model;

/**
 * <p>Exception to indicate a duplicate order was added to an orderbook.</p>
 *
 * @since 0.0.1
 */
public class DuplicateOrderException extends Exception {
  public DuplicateOrderException(SecurityOrder order) {
    super("Duplicate order. id:" + order.getId());
  }
}
