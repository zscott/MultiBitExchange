package org.multibit.exchange.cucumber;

public class QuoteRow {
  private final String bid;
  private final String ask;
  private final String spread;

  public QuoteRow(String bid, String ask, String spread) {

    this.bid = bid;
    this.ask = ask;
    this.spread = spread;
  }

  public String getBid() {
    return bid;
  }

  public String getAsk() {
    return ask;
  }

  public String getSpread() {
    return spread;
  }
}
