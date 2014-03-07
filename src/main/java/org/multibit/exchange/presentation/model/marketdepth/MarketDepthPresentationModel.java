package org.multibit.exchange.presentation.model.marketdepth;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.joda.time.DateTime;
import org.multibit.common.DateUtils;
import org.multibit.common.Entity;
import org.multibit.exchange.presentation.model.common.PresentationModelMetaData;

public class MarketDepthPresentationModel implements Entity<String> {

  @JsonProperty("_id")
  private String _id;

  private String exchangeId;

  private String ticker;

  private PresentationModelMetaData metaData = new PresentationModelMetaData();

  private BidDepthData bidDepthData;

  private AskDepthData askDepthData;

  /*
   * No arg constructor needed for Jackson.
   */
  @JsonCreator
  public MarketDepthPresentationModel() {
  }

  public MarketDepthPresentationModel(String id, String exchangeId, String ticker) {
    this._id = id;
    this.exchangeId = exchangeId;
    this.ticker = ticker;
    metaData.setLastUpdatedTimestamp(DateUtils.nowUtc());
    bidDepthData = new BidDepthData();
    askDepthData = new AskDepthData();
  }

  public String getExchangeId() {
    return exchangeId;
  }

  public void setExchangeId(String exchangeId) {
    this.exchangeId = exchangeId;
  }

  public String getTicker() {
    return ticker;
  }

  public void setTicker(String ticker) {
    this.ticker = ticker;
  }

  public PresentationModelMetaData getMetaData() {
    return metaData;
  }

  public void setMetaData(PresentationModelMetaData metaData) {
    this.metaData = metaData;
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

  public void setUpdateTimestamp(DateTime updateTimestamp) {
    this.metaData.setLastUpdatedTimestamp(updateTimestamp);
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

  @Override
  public String getId() {
    return _id;
  }

  @Override
  public void setId(String id) {
    _id = id;
  }
}
