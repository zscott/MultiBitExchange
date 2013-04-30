package org.multibit.exchange.domainmodel;

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
}
