package org.multibit.exchange.domainmodel;

import com.google.common.base.Strings;

import static com.google.common.base.Preconditions.checkArgument;

public class Currency {

  private String symbol;

  public Currency(String symbol) {

    checkArgument(!Strings.isNullOrEmpty(symbol), "symbol must not be null or empty: '%s'", symbol);

    this.symbol = symbol;
  }

  public String getSymbol() {
    return symbol;
  }

}
