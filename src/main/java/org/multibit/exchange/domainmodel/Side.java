package org.multibit.exchange.domainmodel;

/**
 * <p>Enum to provide type-safe representations of order book sides.</p>
 * <p>Example:</p>
 * <pre>
 *   Side buySide = Side.BUY;
 *   Side sellSide = Side.SELL;
 * </pre>
 *
 * @since 0.0.1
 */
public enum Side {

  BUY, // represents the buy-side of an order book

  SELL // represents the sell-side of an order book
}
