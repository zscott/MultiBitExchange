package org.multibit.exchange.presentation.model.marketdepth;

import com.fasterxml.jackson.annotation.JsonCreator;

public class MarketDepthPresentationModel extends AbstractPresentationModel {

  private String exchangeId;

  private String currencyPairId;

  private BidDepthData bidDepthData;

  private AskDepthData askDepthData;

  /*
   * No arg constructor needed for Jackson.
   */
  @JsonCreator
  public MarketDepthPresentationModel() {
  }

  public MarketDepthPresentationModel(String id, String exchangeId, String currencyPairId) {
    this.setId(id);
    this.exchangeId = exchangeId;
    this.currencyPairId = currencyPairId;
    bidDepthData = new BidDepthData();
    askDepthData = new AskDepthData();
  }

  public String getExchangeId() {
    return exchangeId;
  }

  public void setExchangeId(String exchangeId) {
    this.exchangeId = exchangeId;
  }

  public String getCurrencyPairId() {
    return currencyPairId;
  }

  public void setCurrencyPairId(String currencyPairId) {
    this.currencyPairId = currencyPairId;
  }

  public BidDepthData getBidDepthData() {
    return bidDepthData;
  }

  public void setBidDepthData(BidDepthData bidDepthData) {
    this.bidDepthData = bidDepthData;
  }

  public AskDepthData getAskDepthData() {
    return askDepthData;
  }

  public void setAskDepthData(AskDepthData askDepthData) {
    this.askDepthData = askDepthData;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    MarketDepthPresentationModel that = (MarketDepthPresentationModel) o;

    if (!askDepthData.equals(that.askDepthData)) return false;
    if (!bidDepthData.equals(that.bidDepthData)) return false;
    if (!metaData.equals(that.metaData)) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = metaData.hashCode();
    result = 31 * result + bidDepthData.hashCode();
    result = 31 * result + askDepthData.hashCode();
    return result;
  }

}
