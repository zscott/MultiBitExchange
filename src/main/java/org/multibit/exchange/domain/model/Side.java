package org.multibit.exchange.domain.model;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

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
  ;

  public static Side fromString(String side) {
    Preconditions.checkArgument(!Strings.isNullOrEmpty(side), "side must not be null or empty");
    String upperCaseSide = side.toUpperCase();
    Preconditions.checkArgument(
        upperCaseSide.equals(Side.BUY.toString()) ||
            upperCaseSide.equals(Side.SELL.toString()), "side must be BUY or SELL");

    return Side.valueOf(upperCaseSide);
  }
}
