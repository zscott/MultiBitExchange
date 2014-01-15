package org.multibit.exchange.infrastructure.adaptor.api.integration;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.multibit.exchange.infrastructure.adaptor.api.resources.BuyOrderDescriptor;


import static org.fest.assertions.api.Assertions.assertThat;

public class BuyOrderDescriptorSerializationTest extends BaseDropWizardSerializationTest {

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
    final BuyOrderDescriptor buyOrderDescriptor =
        (BuyOrderDescriptor) deserializeFromJson(json, BuyOrderDescriptor.class);

    // Assert
    assertThat(buyOrderDescriptor.getTickerSymbol()).isEqualTo(expectedTickerSymbol);
    assertThat(buyOrderDescriptor.getOrderAmount()).isEqualTo(expectedOrderAmount);
  }

}
