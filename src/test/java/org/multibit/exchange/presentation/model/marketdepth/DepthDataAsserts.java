package org.multibit.exchange.presentation.model.marketdepth;

import com.google.common.collect.Lists;
import org.multibit.common.jackson.PriceVolume;
import org.multibit.exchange.domain.model.ItemPrice;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.fest.assertions.api.Assertions.assertThat;

public class DepthDataAsserts {

  public static void assertPriceLevelVolumesAndOrder(DepthData depthData, PriceVolume... expectedPriceVolumeArgs) {
    List<PriceVolume> actualPriceVolumeList = getOrderedPriceAndVolume(depthData);
    List<PriceVolume> expectedPriceVolumeList = Lists.newArrayList();
    Collections.addAll(expectedPriceVolumeList, expectedPriceVolumeArgs);
    assertThat(actualPriceVolumeList).isEqualTo(expectedPriceVolumeList);
    for (PriceVolume expectedPriceVolume : expectedPriceVolumeList) {
      String priceLevel = expectedPriceVolume.getPrice();
      assertThat(depthData.getVolumeAtPrice(priceLevel)).isEqualTo(expectedPriceVolume.getVolume());
    }
  }

  public static List<PriceVolume> getOrderedPriceAndVolume(DepthData depthData) {
    Map<ItemPrice, String> priceVolumeMap = depthData.getPriceVolumeMap();
    List<PriceVolume> retVal = Lists.newArrayListWithCapacity(priceVolumeMap.size());
    for (ItemPrice pricedItem : priceVolumeMap.keySet()) {
      retVal.add(new PriceVolume(pricedItem.getBigDecimalPrice().toPlainString(), priceVolumeMap.get(pricedItem)));
    }
    return retVal;
  }
}
