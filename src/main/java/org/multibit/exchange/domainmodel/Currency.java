package org.multibit.exchange.domainmodel;

import com.google.common.base.Strings;


import static com.google.common.base.Preconditions.checkArgument;

/**
 * <p>A currency.</p>
 *
 * @since 0.0.1
 */
public class Currency {

  private String symbol;

  public Currency(String symbol) {

    checkArgument(!Strings.isNullOrEmpty(symbol), "symbol must not be null or empty: '%s'", symbol);

    this.symbol = symbol;
  }

  public String getSymbol() {
    return symbol;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Currency currency = (Currency) o;

    if (!symbol.equals(currency.symbol)) return false;

    return true;
  }

  @Override
  public int hashCode() {
    return symbol.hashCode();
  }

  @Override
  public String toString() {
    return "Currency{" +
        "symbol='" + symbol + '\'' +
        '}';
  }
}
