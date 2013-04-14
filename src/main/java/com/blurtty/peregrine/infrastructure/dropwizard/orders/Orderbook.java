package com.blurtty.peregrine.infrastructure.dropwizard.orders;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.validation.constraints.NotNull;

/**
 * <p>Orderbook to provide the following to the web interface:</p>
 * <ul>
 * <li>A read-only representation of an orderbook</li>
 * </ul>
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
