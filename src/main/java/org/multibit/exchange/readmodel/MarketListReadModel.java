package org.multibit.exchange.readmodel;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.List;

/**
 * <p>A ReadModel for representing a list of Markets:</p>
 *
 * @since 0.0.1
 *         
 */
@JsonPropertyOrder({"count","markets"})
public class MarketListReadModel {

  @JsonProperty
  private final List<MarketReadModel> markets;

  @JsonCreator
  public MarketListReadModel(
    @JsonProperty("markets") List<MarketReadModel> markets) {
    this.markets = markets;
  }

  public List<MarketReadModel> getMarkets() {
    return markets;
  }

  @JsonProperty
  public int getCount() {
    return markets.size();
  }
}
