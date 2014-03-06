package org.multibit.exchange.presentation.model.marketdepth;

import com.yammer.dropwizard.testing.JsonHelpers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.multibit.exchange.domain.model.ItemPrice;
import org.multibit.exchange.domain.model.Side;
import org.multibit.exchange.testing.SideFaker;

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
    BidDepthData depthData = new BidDepthData();

    // Assert
    assertThat(depthData).isNotNull();
    assertThat(depthData.getSide()).isEqualTo(expectedSide);
  }

  @Test
  public void create_forAsks() {
    // Arrange
    Side expectedSide = Side.SELL;

    // Act
    AskDepthData depthData = new AskDepthData();

    // Assert
    assertThat(depthData).isNotNull();
    assertThat(depthData.getSide()).isEqualTo(expectedSide);
  }

  @Test
  public void getPriceLevels_bids_withNoData() {
    // Arrange
    BidDepthData depthData = new BidDepthData();

    // Act
    Set<ItemPrice> priceLevels = depthData.getPriceLevels();

    // Assert
    assertThat(priceLevels).isNotNull();
    assertThat(priceLevels.size()).isZero();
  }

  @Test
  public void getPriceLevels_bids_withOneBid() {
    // Arrange
    BidDepthData depthData = new BidDepthData();
    String price = "10";
    String volume = "100";
    int expectedPriceLevelCount = 1;
    depthData.increaseVolumeAtPrice(price, volume);

    // Act
    Set<ItemPrice> priceLevels = depthData.getPriceLevels();

    // Assert
    assertThat(priceLevels).isNotNull();
    assertThat(priceLevels.size()).isEqualTo(expectedPriceLevelCount);
    assertThat(depthData.getVolumeAtPrice(price)).isEqualTo(volume);
  }

  @Test
  public void getPriceLevels_bids_withTwoBids_samePriceLevel() {
    // Arrange
    BidDepthData depthData = new BidDepthData();
    String price = "10";
    String volume1 = "100";
    String volume2 = "100";
    String expectedVolume = "200";
    int expectedPriceLevelCount = 1;
    depthData.increaseVolumeAtPrice(price, volume1);
    depthData.increaseVolumeAtPrice(price, volume2);

    // Act
    Set<ItemPrice> priceLevels = depthData.getPriceLevels();

    // Assert
    assertThat(priceLevels).isNotNull();
    assertThat(priceLevels.size()).isEqualTo(expectedPriceLevelCount);
    assertThat(depthData.getVolumeAtPrice(price)).isEqualTo(expectedVolume);
  }

  @Test
  public void getPriceLevels_bids_withThreeBids_samePriceLevel() {
    // Arrange
    BidDepthData depthData = new BidDepthData();
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
    Set<ItemPrice> priceLevels = depthData.getPriceLevels();

    // Assert
    assertThat(priceLevels).isNotNull();
    assertThat(priceLevels.size()).isEqualTo(expectedPriceLevelCount);
    assertThat(depthData.getVolumeAtPrice(price)).isEqualTo(expectedVolume);
  }

  @Test
  public void getPriceLevels_bids_withThreeBids_twoPriceLevels() {
    // Arrange
    BidDepthData depthData = new BidDepthData();

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
    Set<ItemPrice> priceLevels = depthData.getPriceLevels();

    // Assert
    assertThat(priceLevels).isNotNull();
    DepthDataAsserts.assertPriceLevelVolumesAndOrder(
            depthData,
            new PriceAndVolume(price1, expectedPrice1Volume),
            new PriceAndVolume(price2, expectedPrice2Volume));
  }

  @Test
  public void getPriceLevels_bids_withThreeBids_threePriceLevels() {
    // Arrange
    BidDepthData depthData = new BidDepthData();

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
    DepthDataAsserts.assertPriceLevelVolumesAndOrder(
            depthData,
            new PriceAndVolume(price1, expectedPrice1Volume),
            new PriceAndVolume(price2, expectedPrice2Volume),
            new PriceAndVolume(price3, expectedPrice3Volume));
  }

  @Test
  public void getPriceLevels_bids_withThreeBidsOutOfOrder_threePriceLevels() {
    // Arrange
    BidDepthData depthData = new BidDepthData();

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
    DepthDataAsserts.assertPriceLevelVolumesAndOrder(
            depthData,
            new PriceAndVolume(price1, expectedPrice1Volume),
            new PriceAndVolume(price2, expectedPrice2Volume),
            new PriceAndVolume(price3, expectedPrice3Volume));
  }

  @Test
  public void getPriceLevels_asks_withThreeBidsOutOfOrder_threePriceLevels() {
    // Arrange
    AskDepthData depthData = new AskDepthData();

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
    DepthDataAsserts.assertPriceLevelVolumesAndOrder(
            depthData,
            new PriceAndVolume(price3, expectedPrice3Volume),
            new PriceAndVolume(price2, expectedPrice2Volume),
            new PriceAndVolume(price1, expectedPrice1Volume)
    );
  }

  @Test
  public void decreaseVolumeAtPrice_byZero() {
    // Arrange
    AskDepthData depthData = new AskDepthData();
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
  public void decreaseVolumeAtPrice_byATinyAmount() {
    // Arrange
    AskDepthData depthData = new AskDepthData();
    String price = "12";
    String increaseVolume = "100";
    String decreaseVolume = "0.0001";
    String expectedVolume = "99.9999";

    depthData.increaseVolumeAtPrice(price, increaseVolume);

    // Act
    depthData.decreaseVolumeAtPrice(price, decreaseVolume);

    // Assert
    DepthDataAsserts.assertPriceLevelVolumesAndOrder(
            depthData,
            new PriceAndVolume(price, expectedVolume)
    );
  }

  @Test
  public void decreaseVolumeAtPrice_someVolumeRemains() {
    // Arrange
    AskDepthData depthData = new AskDepthData();
    String price = "11.887";
    String increaseVolume = "100";
    String decreaseVolume = "99";
    String expectedVolume = "1";

    depthData.increaseVolumeAtPrice(price, increaseVolume);

    // Act
    depthData.decreaseVolumeAtPrice(price, decreaseVolume);

    // Assert
    DepthDataAsserts.assertPriceLevelVolumesAndOrder(
            depthData,
            new PriceAndVolume(price, expectedVolume)
    );
  }

  @Test
  public void decreaseVolumeAtPrice_noVolumeRemains() {
    // Arrange
    AskDepthData depthData = new AskDepthData();
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
  public void decreaseVolumeAtPrice_negativeVolumeRemains() {
    // Arrange
    Side expectedSide = SideFaker.createValid();
    DepthData depthData = expectedSide == Side.BUY ? new BidDepthData() : new AskDepthData();
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

  @Test
  public void testSerializeThenDeserialize_AskDepthData() throws Exception {
    // Arrange
    AskDepthData depthData = new AskDepthData();

    String price1 = "12";
    String price1_volume = "12";

    String price2 = "9.5";
    String price2_volume = "27";

    String price3 = "8.99999999";
    String price3_volume = "25";

    // Act
    depthData.increaseVolumeAtPrice(price2, price2_volume);
    depthData.increaseVolumeAtPrice(price1, price1_volume);
    depthData.increaseVolumeAtPrice(price3, price3_volume);
    String json = JsonHelpers.asJson(depthData);

    // Act
    AskDepthData deserialized = JsonHelpers.fromJson(json, AskDepthData.class);

    // Assert
    assertThat(deserialized).isEqualTo(depthData);
  }

  @Test
  public void testSerializeTheDeserialize_BidDepthData() throws Exception {
    // Arrange
    BidDepthData depthData = new BidDepthData();

    String price1 = "12";
    String price1_volume = "12";

    String price2 = "9.5";
    String price2_volume = "27";

    String price3 = "8.99999999";
    String price3_volume = "25";

    // Act
    depthData.increaseVolumeAtPrice(price2, price2_volume);
    depthData.increaseVolumeAtPrice(price1, price1_volume);
    depthData.increaseVolumeAtPrice(price3, price3_volume);
    String json = JsonHelpers.asJson(depthData);

    // Act
    BidDepthData deserialized = JsonHelpers.fromJson(json, BidDepthData.class);

    // Assert
    assertThat(deserialized).isEqualTo(depthData);
  }
}
