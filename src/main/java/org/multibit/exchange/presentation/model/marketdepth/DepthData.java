package org.multibit.exchange.presentation.model.marketdepth;

import com.google.common.collect.Maps;
import org.multibit.exchange.domain.model.ItemPrice;
import org.multibit.exchange.domain.model.PricedItem;
import org.multibit.exchange.domain.model.PricedItemComparator;
import org.multibit.exchange.domain.model.Side;

import java.util.Set;
import java.util.TreeMap;

public class DepthData {

  private Side side;
  private TreeMap<PricedItem, Long> priceVolumeMap;

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

  public void increaseVolumeAtPrice(String price, long volume) {
    ItemPrice priceLevel = new ItemPrice(price);
    priceVolumeMap.put(priceLevel, getVolumeAtPrice(price) + volume);
  }

  public long getVolumeAtPrice(String price) {
    ItemPrice priceLevel = new ItemPrice(price);
    if (priceVolumeMap.containsKey(priceLevel)) {
      return priceVolumeMap.get(priceLevel);
    } else {
      return 0l;
    }
  }
}
