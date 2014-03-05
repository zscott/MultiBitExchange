package org.multibit.exchange.presentation.model;

import org.junit.Before;
import org.junit.Test;
import org.multibit.exchange.domain.model.PricedItem;
import org.multibit.exchange.domain.model.Side;
import org.multibit.exchange.presentation.model.marketdepth.DepthData;

import java.util.Set;

import static org.fest.assertions.api.Assertions.assertThat;

public class DepthDataTest {

  @Before
  public void setUp() throws Exception {

  }

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
    Side expectedSide = Side.SELL;
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
    Side expectedSide = Side.SELL;
    DepthData depthData = new DepthData(expectedSide);
    String price = "10";
    long volume = 100;
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
    Side expectedSide = Side.SELL;
    DepthData depthData = new DepthData(expectedSide);
    String price = "10";
    long volume1 = 100;
    long volume2 = 100;
    long expectedVolume = 200;
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
    Side expectedSide = Side.SELL;
    DepthData depthData = new DepthData(expectedSide);
    String price = "10";
    long volume1 = 100;
    long volume2 = 50;
    long volume3 = 17;
    long expectedVolume = 167;
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
    Side expectedSide = Side.SELL;
    DepthData depthData = new DepthData(expectedSide);
    String price1 = "10";
    String price2 = "9";
    long p1_volume1 = 100;
    long p1_volume2 = 50;
    long p2_volume3 = 17;
    long p1_expectedVolume = 150;
    long p2_expectedVolume = 17;
    int expectedPriceLevelCount = 2;
    depthData.increaseVolumeAtPrice(price1, p1_volume1);
    depthData.increaseVolumeAtPrice(price1, p1_volume2);
    depthData.increaseVolumeAtPrice(price2, p2_volume3);

    // Act
    Set<PricedItem> priceLevels = depthData.getPriceLevels();

    // Assert
    assertThat(priceLevels).isNotNull();
    assertThat(priceLevels.size()).isEqualTo(expectedPriceLevelCount);
    assertThat(depthData.getVolumeAtPrice(price1)).isEqualTo(p1_expectedVolume);
    assertThat(depthData.getVolumeAtPrice(price2)).isEqualTo(p2_expectedVolume);
  }

}
