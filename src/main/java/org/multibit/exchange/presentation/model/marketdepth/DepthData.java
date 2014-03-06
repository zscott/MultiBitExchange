package org.multibit.exchange.presentation.model.marketdepth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.multibit.common.jackson.ItemPriceKeyDeserializer;
import org.multibit.common.jackson.ItemPriceKeySerializer;
import org.multibit.exchange.domain.model.ItemPrice;
import org.multibit.exchange.domain.model.PricedItemComparator;
import org.multibit.exchange.domain.model.Side;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

public abstract class DepthData {

  @JsonIgnore
  private static final String ZERO_VOLUME = "0";

  private Side side;


  @JsonIgnore
  private SortedMap<ItemPrice, String> priceVolumeMap;

  public DepthData(Side side) {
    this.side = side;
    priceVolumeMap = Maps.newTreeMap(PricedItemComparator.forSide(side));
  }

  @SuppressWarnings("unused")
  @JsonDeserialize(keyUsing = ItemPriceKeyDeserializer.class)
  @JsonSerialize(keyUsing = ItemPriceKeySerializer.class)
  public Map<ItemPrice, String> getPriceVolumeMap() {
    return priceVolumeMap;
  }

  @SuppressWarnings("unused")
  public void setPriceVolumeMap(Map<ItemPrice, String> priceVolumeMap) {
    this.priceVolumeMap.clear();
    this.priceVolumeMap.putAll(priceVolumeMap);
  }

  public Side getSide() {
    return side;
  }

  @JsonIgnore
  public Set<ItemPrice> getPriceLevels() {
    return priceVolumeMap.keySet();
  }

  @JsonIgnore
  public List<PriceAndVolume> getOrderedPriceAndVolume() {
    List<PriceAndVolume> retVal = Lists.newArrayListWithCapacity(priceVolumeMap.size());
    for (ItemPrice pricedItem : priceVolumeMap.keySet()) {
      retVal.add(new PriceAndVolume(pricedItem.getBigDecimalPrice().toPlainString(), priceVolumeMap.get(pricedItem)));
    }
    return retVal;
  }

  @JsonIgnore
  public String getVolumeAtPrice(String price) {
    ItemPrice priceLevel = new ItemPrice(price);
    if (priceVolumeMap.containsKey(priceLevel)) {
      return priceVolumeMap.get(priceLevel);
    } else {
      return ZERO_VOLUME;
    }
  }

  public void increaseVolumeAtPrice(String price, String increaseVolumeBy) {
    ItemPrice priceLevel = new ItemPrice(price);
    BigDecimal newVolume = new BigDecimal(getVolumeAtPrice(price)).add(new BigDecimal(increaseVolumeBy));
    priceVolumeMap.put(priceLevel, newVolume.toPlainString());
  }

  public void decreaseVolumeAtPrice(String price, String decreaseVolumeBy) {
    ItemPrice priceLevel = new ItemPrice(price);
    BigDecimal bigDecimalVolume = new BigDecimal(decreaseVolumeBy);
    Preconditions.checkArgument(bigDecimalVolume.compareTo(BigDecimal.ZERO) > 0, "volume must be greater than zero");

    String volumeAtPrice = getVolumeAtPrice(price);
    BigDecimal bigDecimalVolumeAtPrice = new BigDecimal(volumeAtPrice);
    Preconditions.checkArgument(bigDecimalVolumeAtPrice.subtract(bigDecimalVolume).compareTo(BigDecimal.ZERO) >= 0,
            String.format("Volume cannot be decreased by more than total volume. " +
                    "Total volume at price %s is %s. Cannot decrease by %s.", price, volumeAtPrice, decreaseVolumeBy));

    BigDecimal newVolume = bigDecimalVolumeAtPrice.subtract(bigDecimalVolume);
    if (newVolume.equals(BigDecimal.ZERO)) {
      priceVolumeMap.remove(priceLevel);
    } else {
      priceVolumeMap.put(priceLevel, newVolume.toPlainString());
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    DepthData depthData = (DepthData) o;

    if (!priceVolumeMap.equals(depthData.priceVolumeMap)) return false;
    if (side != depthData.side) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = side.hashCode();
    result = 31 * result + priceVolumeMap.hashCode();
    return result;
  }
}
