package org.multibit.exchange.domain.model;

/**
 * <p>A currency.</p>
 *
 * @since 0.0.1
 */
public class Currency extends TradeableItem {

  public Currency(String symbol) {
    super(symbol);
  }

  @Override
  public String toString() {
    return "Currency{" +
        "symbol='" + getSymbol() + '\'' +
        '}';
  }
}
