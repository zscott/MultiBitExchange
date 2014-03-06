package org.multibit.exchange.presentation.model.marketdepth;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.multibit.exchange.domain.model.ItemPrice;
import org.multibit.exchange.domain.model.PricedItem;
import org.multibit.exchange.domain.model.PricedItemComparator;
import org.multibit.exchange.domain.model.Side;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

public class DepthData {

  private static final String ZERO_VOLUME = "0";

  private Side side;

  private TreeMap<PricedItem, String> priceVolumeMap;

  public DepthData(Side side) {
    this.side = side;
    priceVolumeMap = Maps.newTreeMap(PricedItemComparator.forSide(side));
  }

  public Side getSide() {
    return side;
  }

  public Set<PricedItem> getPriceLevels() {
    return priceVolumeMap.keySet();
  }

  public List<PriceAndVolume> getOrderedPriceAndVolume() {
    List<PriceAndVolume> retVal = Lists.newArrayListWithCapacity(priceVolumeMap.size());
    for (PricedItem pricedItem : priceVolumeMap.keySet()) {
      retVal.add(new PriceAndVolume(pricedItem.getBigDecimalPrice().toPlainString(), priceVolumeMap.get(pricedItem)));
    }
    return retVal;
  }

  public void increaseVolumeAtPrice(String price, String volume) {
    ItemPrice priceLevel = new ItemPrice(price);
    BigDecimal newVolume = new BigDecimal(getVolumeAtPrice(price)).add(new BigDecimal(volume));
    priceVolumeMap.put(priceLevel, newVolume.toPlainString());
  }

  public void decreaseVolumeAtPrice(String price, String volume) {
    ItemPrice priceLevel = new ItemPrice(price);
    BigDecimal bigDecimalVolume = new BigDecimal(volume);
    Preconditions.checkArgument(bigDecimalVolume.compareTo(BigDecimal.ZERO) > 0, "volume must be greater than zero");

    BigDecimal newVolume = new BigDecimal(getVolumeAtPrice(price)).subtract(bigDecimalVolume);
    priceVolumeMap.put(priceLevel, newVolume.toPlainString());
  }

  public String getVolumeAtPrice(String price) {
    ItemPrice priceLevel = new ItemPrice(price);
    if (priceVolumeMap.containsKey(priceLevel)) {
      return priceVolumeMap.get(priceLevel);
    } else {
      return ZERO_VOLUME;
    }
  }
}
