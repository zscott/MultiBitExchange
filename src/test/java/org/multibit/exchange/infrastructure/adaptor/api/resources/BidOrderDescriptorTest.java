package org.multibit.exchange.infrastructure.adaptor.api.resources;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.multibit.exchange.infrastructure.adaptor.api.BaseDropWizardSerializationTest;


import static org.fest.assertions.api.Assertions.assertThat;

public class BidOrderDescriptorTest extends BaseDropWizardSerializationTest {

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Test
  public void testDeserializationFromJson_validJson() throws Exception {
    // Arrange
    String expectedTickerSymbol = "BTC/CAD";
    String expectedOrderAmount = "10.5";
    String json =
        "{" +
            "\"tickerSymbol\":\"" + expectedTickerSymbol + "\", " +
            "\"orderAmount\":\"" + expectedOrderAmount + "\"" +
            "}";

    // Act
    final BidOrderDescriptor bidOrderDescriptor =
        (BidOrderDescriptor) deserializeFromJson(json, BidOrderDescriptor.class);

    // Assert
    assertThat(bidOrderDescriptor.getTickerSymbol()).isEqualTo(expectedTickerSymbol);
    assertThat(bidOrderDescriptor.getOrderAmount()).isEqualTo(expectedOrderAmount);
  }

}
