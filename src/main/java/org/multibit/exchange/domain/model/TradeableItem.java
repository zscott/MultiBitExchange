package org.multibit.exchange.domain.model;

import com.google.common.base.Strings;

import java.util.Locale;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * <p>TradeableItem provides the following to the domain model:</p>
 * <ul>
 * <li>A ValueObjct representing an item that may be traded.</li>
 * </ul>
 *
 * @since 0.0.1
 *         
 */
public abstract class TradeableItem {

  private final String symbol;

  public TradeableItem(String symbol) {

    checkArgument(!Strings.isNullOrEmpty(symbol), "symbol must not be null or empty: '%s'", symbol);

    this.symbol = symbol.toUpperCase(Locale.CANADA);
  }

  public String getSymbol() {
    return symbol;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    TradeableItem that = (TradeableItem) o;

    if (!symbol.equals(that.symbol)) return false;

    return true;
  }

  @Override
  public int hashCode() {
    return symbol.hashCode();
  }
}
