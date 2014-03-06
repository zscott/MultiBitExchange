package org.multibit.exchange.presentation.model.marketdepth;

import com.google.common.collect.Lists;
import org.multibit.exchange.domain.model.ItemPrice;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.fest.assertions.api.Assertions.assertThat;

public class DepthDataAsserts {

  public static void assertPriceLevelVolumesAndOrder(DepthData depthData, PriceAndVolume... expectedPriceAndVolumeArgs) {
    List<PriceAndVolume> actualPriceAndVolumeList = getOrderedPriceAndVolume(depthData);
    List<PriceAndVolume> expectedPriceAndVolumeList = Lists.newArrayList();
    Collections.addAll(expectedPriceAndVolumeList, expectedPriceAndVolumeArgs);
    assertThat(actualPriceAndVolumeList).isEqualTo(expectedPriceAndVolumeList);
    for (PriceAndVolume expectedPriceAndVolume : expectedPriceAndVolumeList) {
      String priceLevel = expectedPriceAndVolume.getPrice();
      assertThat(depthData.getVolumeAtPrice(priceLevel)).isEqualTo(expectedPriceAndVolume.getVolume());
    }
  }

  public static List<PriceAndVolume> getOrderedPriceAndVolume(DepthData depthData) {
    Map<ItemPrice, String> priceVolumeMap = depthData.getPriceVolumeMap();
    List<PriceAndVolume> retVal = Lists.newArrayListWithCapacity(priceVolumeMap.size());
    for (ItemPrice pricedItem : priceVolumeMap.keySet()) {
      retVal.add(new PriceAndVolume(pricedItem.getBigDecimalPrice().toPlainString(), priceVolumeMap.get(pricedItem)));
    }
    return retVal;
  }
}
