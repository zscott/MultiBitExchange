package org.multibit.exchange.infrastructure.adaptor.web.restapi.readmodel;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.multibit.common.Entity;

/**
 * <p>ReadModel to provide the following to REST clients:</p>
 * <ul>
 * <li>A read only representation of a Quote for a Security.</li>
 * </ul>
 *
 * @since 0.0.1
 */
@JsonPropertyOrder({"exchangeId", "ticker", "bid", "ask", "timestamp"})
public class QuoteReadModel implements Entity<String> {

  @JsonProperty("_id")
  private String _id;
  private String exchangeId;
  private String ticker;
  private String bid;
  private String ask;
  private String timestamp;

  @JsonCreator
  @SuppressWarnings("unused")
  public QuoteReadModel() {
  }

  public QuoteReadModel(String _id, String exchangeId, String ticker, String bid, String ask, String timestamp) {
    this._id = _id;
    this.exchangeId = exchangeId;
    this.ticker = ticker;
    this.bid = bid;
    this.ask = ask;
    this.timestamp = timestamp;
  }

  @JsonIgnore
  public String getId() {
    return _id;
  }

  public void setId(String id) {
    this._id = id;
  }

  public String getExchangeId() {
    return exchangeId;
  }

  public String getTicker() {
    return ticker;
  }

  public String getBid() {
    return bid;
  }

  public String getAsk() {
    return ask;
  }

  public String getTimestamp() {
    return timestamp;
  }
}
