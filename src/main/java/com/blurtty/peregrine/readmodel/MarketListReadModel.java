package com.blurtty.peregrine.readmodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


import java.util.List;

/**
 * <p>[Pattern] to provide the following to {@link Object}:</p>
 * <ul>
 * <li></li>
 * </ul>
 * <p>Example:</p>
 * <pre>
 * </pre>
 *
 * @since 0.0.1
 *         
 */
@JsonPropertyOrder({"count","markets"})
public class MarketListReadModel {

  @JsonProperty
  private final List<MarketReadModel> markets;

  public MarketListReadModel(List<MarketReadModel> markets) {
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
