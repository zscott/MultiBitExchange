package org.multibit.exchange.presentation.model.marketdepth;

import org.multibit.exchange.domain.model.PricedItem;

import java.math.BigDecimal;

public class PriceAndVolume implements PricedItem {

  private BigDecimal bigDecimalPrice;

  private BigDecimal bigDecimalVolume;

  public PriceAndVolume(String price, String volume) {
    this.bigDecimalPrice = new BigDecimal(price);
    this.bigDecimalVolume = new BigDecimal(volume);
  }

  @Override
  public BigDecimal getBigDecimalPrice() {
    return bigDecimalPrice;
  }

  public String getPrice() {
    return bigDecimalPrice.toPlainString();
  }

  public BigDecimal getBigDecimalVolume() {
    return bigDecimalVolume;
  }

  public String getVolume() {
    return bigDecimalVolume.toPlainString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    PriceAndVolume that = (PriceAndVolume) o;

    if (!bigDecimalPrice.equals(that.bigDecimalPrice)) return false;
    if (!bigDecimalVolume.equals(that.bigDecimalVolume)) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = bigDecimalPrice.hashCode();
    result = 31 * result + bigDecimalVolume.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "VolumeAtPrice{" +
            "bigDecimalPrice=" + bigDecimalPrice +
            ", bigDecimalVolume=" + bigDecimalVolume +
            '}';
  }
}
