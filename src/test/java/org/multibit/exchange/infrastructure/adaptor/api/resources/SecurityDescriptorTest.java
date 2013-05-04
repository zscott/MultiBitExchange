package org.multibit.exchange.infrastructure.adaptor.api.resources;

import com.fasterxml.jackson.databind.JsonMappingException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.multibit.exchange.infrastructure.adaptor.api.BaseDropWizardSerializationTest;


import static org.fest.assertions.api.Assertions.assertThat;

public class SecurityDescriptorTest extends BaseDropWizardSerializationTest {

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Test
  public void testDeserializationFromJson_validJson() throws Exception {
    // Arrange
    String expectedTickerSymbol = "BTC/CAD";
    String expectedTradeableItemSymbol = "BTC";
    String expectedCurrencySymbol = "CAD";
    String json =
        "{" +
            "\"tickerSymbol\":\"" + expectedTickerSymbol + "\", " +
            "\"tradeableItemSymbol\":\"" + expectedTradeableItemSymbol + "\", " +
            "\"currencySymbol\":\"" + expectedCurrencySymbol + "\"" +
            "}";

    // Act
    final SecurityDescriptor deserializedObject =
        (SecurityDescriptor) deserializeFromJson(json, SecurityDescriptor.class);

    // Assert
    assertThat(deserializedObject.getTickerSymbol()).isEqualTo(expectedTickerSymbol);
    assertThat(deserializedObject.getTradeableItemSymbol()).isEqualTo(expectedTradeableItemSymbol);
    assertThat(deserializedObject.getCurrencySymbol()).isEqualTo(expectedCurrencySymbol);
  }

  @Test
  public void testSecurityDescriptorDeserialization_validJson_extraField() throws Exception {
    // Arrange
    String expectedTickerSymbol = "BTC/CAD";
    String expectedTradeableItemSymbol = "BTC";
    String expectedCurrencySymbol = "CAD";
    String json =
        "{" +
            "\"tickerSymbol\":\"" + expectedTickerSymbol + "\", " +
            "\"tradeableItemSymbol\":\"" + expectedTradeableItemSymbol + "\", " +
            "\"currencySymbol\":\"" + expectedCurrencySymbol + "\", " +
            "\"extraField\":\"extraFieldValue\"" +
            "}";

    // Act
    final SecurityDescriptor deserializedObject =
        (SecurityDescriptor) deserializeFromJson(json, SecurityDescriptor.class);

    // Assert
    assertThat(deserializedObject.getTickerSymbol()).isEqualTo(expectedTickerSymbol);
    assertThat(deserializedObject.getTradeableItemSymbol()).isEqualTo(expectedTradeableItemSymbol);
    assertThat(deserializedObject.getCurrencySymbol()).isEqualTo(expectedCurrencySymbol);
  }

  @Test
  public void testSecurityDescriptorDeserialization_invalidJson_symbols() throws Exception {
    // Arrange
    String expectedTickerSymbol = "BTC/CAD";
    String expectedTradeableItemSymbol = "BTC";
    String expectedCurrencySymbol = "CAD";
    String json =
        "{" +
            "\"intentionalTypoInFieldName_tickerSymbol\":\"" + expectedTickerSymbol + "\", " +
            "\"tradeableItemSymbol\":\"" + expectedTradeableItemSymbol + "\", " +
            "\"currencySymbol\":\"" + expectedCurrencySymbol + "\"" +
            "}";
    thrown.expect(JsonMappingException.class);
    thrown.expectMessage("tickerSymbol must not be null or empty: 'null");

    // Act
    deserializeFromJson(json, SecurityDescriptor.class);
  }
}
