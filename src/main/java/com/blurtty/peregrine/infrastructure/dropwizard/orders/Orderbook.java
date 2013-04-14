package com.blurtty.peregrine.infrastructure.dropwizard.orders;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.validation.constraints.NotNull;

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
@JsonPropertyOrder({"symbol"})
public class Orderbook {

  @JsonProperty
  @NotNull
  private String symbol;

  public Orderbook() {
    symbol = "PERGBTC";
  }
}
