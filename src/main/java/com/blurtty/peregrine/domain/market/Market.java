package com.blurtty.peregrine.domain.market;

/**
 * <p>A Market represents a single currency resources.</p>
 *
 * @since 0.0.1
 *         
 */
public class Market {

  /**
   * The symbol representing the resources. e.g. peregrineBTCCAD for a BTCCAD resources.
   */
  private final String symbol;

  /**
   * The item's symbol. This represents the item being traded for 'local' currency. e.g. BTC could represent bitcoins.
   */
  private final String itemSymbol;

  /**
   * The currency's symbol. This represents the 'local' currency being used to trade. e.g. GBP could represent pounds.
   */
  private final String currencySymbol;


  /**
   * Creates a Market.
   *
   * @param symbol          The symbol representing the resources. e.g. peregrineBTCCAD for a BTCCAD resources.
   * @param itemSymbol      The item's symbol. This represents the item being traded for 'local' currency. e.g. BTC could represent bitcoins.
   * @param currencySymbol  The currency's symbol. This represents the 'local' currency being used to trade. e.g. GBP could represent pounds.
   */
  public Market(String symbol, String itemSymbol, String currencySymbol) {

    this.symbol = symbol;
    this.itemSymbol = itemSymbol;
    this.currencySymbol = currencySymbol;
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
}
