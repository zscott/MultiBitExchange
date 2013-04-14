package com.blurtty.peregrine.domain;

/**
 * <p>A Market represents a single currency market.</p>
 *
 * @since 0.0.1
 *         
 */
public class Market {
  private final String symbol;
  private final String itemSymbol;
  private final String currencySymbol;

  public Market(String symbol, String itemSymbol, String currencySymbol) {

    this.symbol = symbol;
    this.itemSymbol = itemSymbol;
    this.currencySymbol = currencySymbol;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Market market = (Market) o;

    if (!symbol.equals(market.symbol)) return false;

    return true;
  }

  @Override
  public int hashCode() {
    return symbol.hashCode();
  }

  public String getSymbol() {
    return symbol;
  }

  public String getItemSymbol() {
    return itemSymbol;
  }

  public String getCurrencySymbol() {
    return currencySymbol;
  }

  @Override
  public String toString() {
    return "Market{" +
        "symbol='" + symbol + '\'' +
        ", itemSymbol='" + itemSymbol + '\'' +
        ", currencySymbol='" + currencySymbol + '\'' +
        '}';
  }
}
