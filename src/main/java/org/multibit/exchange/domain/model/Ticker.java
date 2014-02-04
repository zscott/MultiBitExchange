package org.multibit.exchange.domain.model;

import com.google.common.base.Strings;

import static com.google.common.base.Preconditions.checkArgument;

public class Ticker {
  private final String symbol;

  public Ticker(String symbol) {
    checkArgument(!Strings.isNullOrEmpty(symbol), "ticker symbol must not be null or empty: '%s'", symbol);

    this.symbol = symbol;
  }

  public String getSymbol() {
    return symbol;
  }

  public boolean isACurrencyPair() {
        return true;
  }

    @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Ticker ticker = (Ticker) o;

    if (!symbol.equals(ticker.symbol)) return false;

    return true;
  }

  @Override
  public int hashCode() {
    return symbol.hashCode();
  }

  @Override
  public String toString() {
    return "Ticker{" +
        "symbol='" + symbol + '\'' +
        '}';
  }

}
