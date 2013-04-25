package org.multibit.exchange.infrastructure.dropwizard.resources;

import com.fasterxml.jackson.databind.JsonMappingException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import org.multibit.exchange.infrastructure.dropwizard.BaseDropWizardSerializationTest;


import static org.fest.assertions.api.Assertions.assertThat;

public class MarketDescriptorDeserializationTest extends BaseDropWizardSerializationTest {

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Test
  public void testMarketDescriptorDeserialization_validJson() throws Exception {
    // Arrange
    String expectedSymbol = "multibitCAD";
    String expectedItemSymbol = "BTC";
    String expectedCurrencySymbol = "CAD";
    String json =
        "{" +
            "\"symbol\":\"" + expectedSymbol + "\", " +
            "\"itemSymbol\":\"" + expectedItemSymbol + "\", " +
            "\"currencySymbol\":\"" + expectedCurrencySymbol + "\"" +
            "}";

    // Act
    final MarketDescriptor deserializedObject = (MarketDescriptor) deserializeFromJson(json, MarketDescriptor.class);

    // Assert
    assertThat(deserializedObject.getSymbol()).isEqualTo(expectedSymbol);
    assertThat(deserializedObject.getItemSymbol()).isEqualTo(expectedItemSymbol);
    assertThat(deserializedObject.getCurrencySymbol()).isEqualTo(expectedCurrencySymbol);
  }

  @Test
  public void testMarketDescriptorDeserialization_validJson_extraField() throws Exception {
    // Arrange
    String expectedSymbol = "multibitCAD";
    String expectedItemSymbol = "BTC";
    String expectedCurrencySymbol = "CAD";
    String json =
        "{" +
            "\"symbol\":\"" + expectedSymbol + "\", " +
            "\"itemSymbol\":\"" + expectedItemSymbol + "\", " +
            "\"currencySymbol\":\"" + expectedCurrencySymbol + "\", " +
            "\"extraField\":\"extraFieldValue\"" +
            "}";

    // Act
    final MarketDescriptor deserializedObject = (MarketDescriptor) deserializeFromJson(json, MarketDescriptor.class);

    // Assert
    assertThat(deserializedObject.getSymbol()).isEqualTo(expectedSymbol);
    assertThat(deserializedObject.getItemSymbol()).isEqualTo(expectedItemSymbol);
    assertThat(deserializedObject.getCurrencySymbol()).isEqualTo(expectedCurrencySymbol);
  }

  @Test
  public void testMarketDescriptorDeserialization_invalidJson_symbols() throws Exception {
    // Arrange
    String expectedSymbol = "multibitCAD";
    String expectedItemSymbol = "BTC";
    String expectedCurrencySymbol = "CAD";
    String json =
        "{" +
            "\"symbols\":\"" + expectedSymbol + "\", " +
            "\"itemSymbol\":\"" + expectedItemSymbol + "\", " +
            "\"currencySymbol\":\"" + expectedCurrencySymbol + "\"" +
            "}";
    thrown.expect(JsonMappingException.class);
    //todo - figure out how matchers work and fix the following line
    //thrown.expectCause(equals(NullPointerException.class));
    thrown.expectMessage("market symbol must not be null or empty: 'null");

    // Act
    deserializeFromJson(json, MarketDescriptor.class);
  }
}
