package org.multibit.exchange.infrastructure.adaptor.web.restapi.readmodel;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.bson.types.ObjectId;
import org.multibit.common.DateUtils;
import org.multibit.common.Entity;
import org.multibit.exchange.domain.model.ItemPrice;
import org.multibit.exchange.domain.model.Side;

import java.util.NoSuchElementException;

/**
 * <p>ReadModel to provide the following to REST clients:</p>
 * <ul>
 * <li>A read only representation of a Quote for a Security.</li>
 * </ul>
 *
 * @since 0.0.1
 */
public class QuoteReadModel implements Entity<String> {

  @JsonProperty("_id")
  private String _id;
  private String exchangeId;
  private String ticker;
  private OrderBookReadModel bids;
  private OrderBookReadModel asks;
  private String timestamp;

  @JsonCreator
  @SuppressWarnings("unused")
  public QuoteReadModel() {
    bids = new OrderBookReadModel(Side.BUY);
    asks = new OrderBookReadModel(Side.SELL);
  }

  public QuoteReadModel(String exchangeId, String ticker, OrderBookReadModel bids, OrderBookReadModel asks) {
    this._id = new ObjectId().toString();
    this.exchangeId = exchangeId;
    this.ticker = ticker;
    this.bids = bids;
    this.asks = asks;
    this.timestamp = generateTimestamp();
  }

  private String generateTimestamp() {
    return DateUtils.formatISO8601(DateUtils.nowUtc());
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
    try {
      return getBidLimitPrice().getRaw();
    } catch (NoSuchElementException e) {
      return null;
    }
  }

  private ItemPrice getBidLimitPrice() {
    return bids.getTop().getLimitPrice();
  }

  public String getAsk() {
    try {
      return getAskLimitPrice().getRaw();
    } catch (NoSuchElementException e) {
      return null;
    }
  }

  private ItemPrice getAskLimitPrice() {
    return asks.getTop().getLimitPrice();
  }

  public String getTimestamp() {
    return timestamp;
  }

  public String getSpread() {
    try {
      return getAskLimitPrice().toBigDecimal().subtract(getBidLimitPrice().toBigDecimal()).toString();
    } catch (NoSuchElementException e) {
      return null;
    }
  }
}
