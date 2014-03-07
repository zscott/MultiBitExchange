package org.multibit.common.jackson;

import com.fasterxml.jackson.annotation.JsonCreator;

public class PriceVolume {

  private String price;

  private String volume;

  @JsonCreator
  public PriceVolume() {
  }

  public PriceVolume(String price, String volume) {
    this.price = price;
    this.volume = volume;
  }

  public String getPrice() {
    return price;
  }

  public String getVolume() {
    return volume;
  }
}
