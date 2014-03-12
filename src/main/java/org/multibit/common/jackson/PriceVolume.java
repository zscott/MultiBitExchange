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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    PriceVolume that = (PriceVolume) o;

    if (price != null ? !price.equals(that.price) : that.price != null) return false;
    if (volume != null ? !volume.equals(that.volume) : that.volume != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = price != null ? price.hashCode() : 0;
    result = 31 * result + (volume != null ? volume.hashCode() : 0);
    return result;
  }
}
