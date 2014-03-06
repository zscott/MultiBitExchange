package org.multibit.exchange.presentation.model;

import com.google.common.collect.Lists;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.multibit.exchange.domain.model.PricedItem;
import org.multibit.exchange.domain.model.Side;
import org.multibit.exchange.presentation.model.marketdepth.DepthData;
import org.multibit.exchange.presentation.model.marketdepth.PriceAndVolume;
import org.multibit.exchange.testing.SideFaker;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.fest.assertions.api.Assertions.assertThat;

public class DepthDataTest {

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Test
  public void create_forBids() {
    // Arrange
    Side expectedSide = Side.BUY;

    // Act
    DepthData depthData = new DepthData(expectedSide);

    // Assert
    assertThat(depthData).isNotNull();
    assertThat(depthData.getSide()).isEqualTo(expectedSide);
  }

  @Test
  public void create_forAsks() {
    // Arrange
    Side expectedSide = Side.SELL;

    // Act
    DepthData depthData = new DepthData(expectedSide);

    // Assert
    assertThat(depthData).isNotNull();
    assertThat(depthData.getSide()).isEqualTo(expectedSide);
  }

  @Test
  public void getPriceLevels_bids_withNoData() {
    // Arrange
    Side expectedSide = Side.BUY;
    DepthData depthData = new DepthData(expectedSide);

    // Act
    Set<PricedItem> priceLevels = depthData.getPriceLevels();

    // Assert
    assertThat(priceLevels).isNotNull();
    assertThat(priceLevels.size()).isZero();
  }

  @Test
  public void getPriceLevels_bids_withOneBid() {
    // Arrange
    Side expectedSide = Side.BUY;
    DepthData depthData = new DepthData(expectedSide);
    String price = "10";
    String volume = "100";
    int expectedPriceLevelCount = 1;
    depthData.increaseVolumeAtPrice(price, volume);

    // Act
    Set<PricedItem> priceLevels = depthData.getPriceLevels();

    // Assert
    assertThat(priceLevels).isNotNull();
    assertThat(priceLevels.size()).isEqualTo(expectedPriceLevelCount);
    assertThat(depthData.getVolumeAtPrice(price)).isEqualTo(volume);
  }

  @Test
  public void getPriceLevels_bids_withTwoBids_samePriceLevel() {
    // Arrange
    Side expectedSide = Side.BUY;
    DepthData depthData = new DepthData(expectedSide);
    String price = "10";
    String volume1 = "100";
    String volume2 = "100";
    String expectedVolume = "200";
    int expectedPriceLevelCount = 1;
    depthData.increaseVolumeAtPrice(price, volume1);
    depthData.increaseVolumeAtPrice(price, volume2);

    // Act
    Set<PricedItem> priceLevels = depthData.getPriceLevels();

    // Assert
    assertThat(priceLevels).isNotNull();
    assertThat(priceLevels.size()).isEqualTo(expectedPriceLevelCount);
    assertThat(depthData.getVolumeAtPrice(price)).isEqualTo(expectedVolume);
  }

  @Test
  public void getPriceLevels_bids_withThreeBids_samePriceLevel() {
    // Arrange
    Side expectedSide = Side.BUY;
    DepthData depthData = new DepthData(expectedSide);
    String price = "10";
    String volume1 = "100";
    String volume2 = "50";
    String volume3 = "17";
    String expectedVolume = "167";
    int expectedPriceLevelCount = 1;
    depthData.increaseVolumeAtPrice(price, volume1);
    depthData.increaseVolumeAtPrice(price, volume2);
    depthData.increaseVolumeAtPrice(price, volume3);

    // Act
    Set<PricedItem> priceLevels = depthData.getPriceLevels();

    // Assert
    assertThat(priceLevels).isNotNull();
    assertThat(priceLevels.size()).isEqualTo(expectedPriceLevelCount);
    assertThat(depthData.getVolumeAtPrice(price)).isEqualTo(expectedVolume);
  }

  @Test
  public void getPriceLevels_bids_withThreeBids_twoPriceLevels() {
    // Arrange
    Side expectedSide = Side.BUY;
    DepthData depthData = new DepthData(expectedSide);

    String price1 = "10";
    String price1_volume1 = "100";
    String price1_volume2 = "50";

    String price2 = "9";
    String price2_volume = "17";

    String expectedPrice1Volume = "150";
    String expectedPrice2Volume = "17";

    depthData.increaseVolumeAtPrice(price1, price1_volume1);
    depthData.increaseVolumeAtPrice(price1, price1_volume2);
    depthData.increaseVolumeAtPrice(price2, price2_volume);

    // Act
    Set<PricedItem> priceLevels = depthData.getPriceLevels();

    // Assert
    assertThat(priceLevels).isNotNull();
    assertPriceLevelVolumesAndOrder(
            depthData,
            new PriceAndVolume(price1, expectedPrice1Volume),
            new PriceAndVolume(price2, expectedPrice2Volume));
  }

  @Test
  public void getPriceLevels_bids_withThreeBids_threePriceLevels() {
    // Arrange
    Side expectedSide = Side.BUY;
    DepthData depthData = new DepthData(expectedSide);

    String price1 = "10";
    String price1_volume = "100";

    String price2 = "9";
    String price2_volume = "17";

    String price3 = "8.99999999";
    String price3_volume = "250";

    String expectedPrice1Volume = "100";
    String expectedPrice2Volume = "17";
    String expectedPrice3Volume = "250";

    // Act
    depthData.increaseVolumeAtPrice(price1, price1_volume);
    depthData.increaseVolumeAtPrice(price2, price2_volume);
    depthData.increaseVolumeAtPrice(price3, price3_volume);

    // Assert
    assertPriceLevelVolumesAndOrder(
            depthData,
            new PriceAndVolume(price1, expectedPrice1Volume),
            new PriceAndVolume(price2, expectedPrice2Volume),
            new PriceAndVolume(price3, expectedPrice3Volume));
  }

  @Test
  public void getPriceLevels_bids_withThreeBidsOutOfOrder_threePriceLevels() {
    // Arrange
    Side expectedSide = Side.BUY;
    DepthData depthData = new DepthData(expectedSide);

    String price1 = "10";
    String price1_volume = "100";

    String price2 = "9";
    String price2_volume = "17";

    String price3 = "8.99999999";
    String price3_volume = "250";

    String expectedPrice1Volume = "100";
    String expectedPrice2Volume = "17";
    String expectedPrice3Volume = "250";

    // Act
    depthData.increaseVolumeAtPrice(price2, price2_volume);
    depthData.increaseVolumeAtPrice(price1, price1_volume);
    depthData.increaseVolumeAtPrice(price3, price3_volume);

    // Assert
    assertPriceLevelVolumesAndOrder(
            depthData,
            new PriceAndVolume(price1, expectedPrice1Volume),
            new PriceAndVolume(price2, expectedPrice2Volume),
            new PriceAndVolume(price3, expectedPrice3Volume));
  }

  @Test
  public void getPriceLevels_asks_withThreeBidsOutOfOrder_threePriceLevels() {
    // Arrange
    Side expectedSide = Side.SELL;
    DepthData depthData = new DepthData(expectedSide);

    String price1 = "12";
    String price1_volume = "12";

    String price2 = "9.5";
    String price2_volume = "27";

    String price3 = "8.99999999";
    String price3_volume = "25";

    String expectedPrice1Volume = "12";
    String expectedPrice2Volume = "27";
    String expectedPrice3Volume = "25";

    // Act
    depthData.increaseVolumeAtPrice(price2, price2_volume);
    depthData.increaseVolumeAtPrice(price1, price1_volume);
    depthData.increaseVolumeAtPrice(price3, price3_volume);

    // Assert
    assertPriceLevelVolumesAndOrder(
            depthData,
            new PriceAndVolume(price3, expectedPrice3Volume),
            new PriceAndVolume(price2, expectedPrice2Volume),
            new PriceAndVolume(price1, expectedPrice1Volume)
    );
  }

  @Test
  public void reducePriceLevel_byZero() {
    // Arrange
    Side expectedSide = Side.SELL;
    DepthData depthData = new DepthData(expectedSide);
    String price = "12";
    String volume = "100";
    String zeroVolume = "0";

    depthData.increaseVolumeAtPrice(price, volume);
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("volume must be greater than zero");

    // Act
    depthData.decreaseVolumeAtPrice(price, zeroVolume);
  }

  @Test
  public void reducePriceLevel_byATinyAmount() {
    // Arrange
    Side expectedSide = Side.SELL;
    DepthData depthData = new DepthData(expectedSide);
    String price = "12";
    String increaseVolume = "100";
    String decreaseVolume = "0.0001";
    String expectedVolume = "99.9999";

    depthData.increaseVolumeAtPrice(price, increaseVolume);

    // Act
    depthData.decreaseVolumeAtPrice(price, decreaseVolume);

    // Assert
    assertPriceLevelVolumesAndOrder(
            depthData,
            new PriceAndVolume(price, expectedVolume)
    );
  }

  @Test
  public void reducePriceLevel_someVolumeRemains() {
    // Arrange
    Side expectedSide = Side.SELL;
    DepthData depthData = new DepthData(expectedSide);
    String price = "11.887";
    String increaseVolume = "100";
    String decreaseVolume = "99";
    String expectedVolume = "1";

    depthData.increaseVolumeAtPrice(price, increaseVolume);

    // Act
    depthData.decreaseVolumeAtPrice(price, decreaseVolume);

    // Assert
    assertPriceLevelVolumesAndOrder(
            depthData,
            new PriceAndVolume(price, expectedVolume)
    );
  }

  @Test
  public void reducePriceLevel_noVolumeRemains() {
    // Arrange
    Side expectedSide = Side.SELL;
    DepthData depthData = new DepthData(expectedSide);
    String price = "11.887";
    String increaseVolume = "100";
    String decreaseVolume = "100";
    String expectedVolume = "0";
    int expectedPriceLevelCount = 0;

    depthData.increaseVolumeAtPrice(price, increaseVolume);

    // Act
    depthData.decreaseVolumeAtPrice(price, decreaseVolume);

    // Assert
    assertThat(depthData.getPriceLevels().size()).isEqualTo(expectedPriceLevelCount);
    assertThat(depthData.getVolumeAtPrice(price)).isEqualTo(expectedVolume);
  }

  @Test
  public void reducePriceLevel_negativeVolumeRemains() {
    // Arrange
    Side expectedSide = SideFaker.createValid();
    DepthData depthData = new DepthData(expectedSide);
    String price = "11.887";
    String increaseVolume = "100.001";
    String decreaseVolume = "100.002";
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage(
            String.format("Volume cannot be decreased by more than total volume. " +
                    "Total volume at price %s is %s. Cannot decrease by %s.", price, increaseVolume, decreaseVolume));

    depthData.increaseVolumeAtPrice(price, increaseVolume);

    // Act
    depthData.decreaseVolumeAtPrice(price, decreaseVolume);
  }

  private void assertPriceLevelVolumesAndOrder(DepthData depthData, PriceAndVolume... expectedPriceAndVolumeArgs) {
    List<PriceAndVolume> actualPriceAndVolumeList = depthData.getOrderedPriceAndVolume();
    List<PriceAndVolume> expectedPriceAndVolumeList = Lists.newArrayList();
    Collections.addAll(expectedPriceAndVolumeList, expectedPriceAndVolumeArgs);
    assertThat(actualPriceAndVolumeList).isEqualTo(expectedPriceAndVolumeList);
    for (PriceAndVolume expectedPriceAndVolume : expectedPriceAndVolumeList) {
      String priceLevel = expectedPriceAndVolume.getPrice();
      assertThat(depthData.getVolumeAtPrice(priceLevel)).isEqualTo(expectedPriceAndVolume.getVolume());
    }
  }
}
