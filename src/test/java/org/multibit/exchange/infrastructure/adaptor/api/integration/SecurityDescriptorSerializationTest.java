package org.multibit.exchange.infrastructure.adaptor.api.integration;

import com.fasterxml.jackson.databind.JsonMappingException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.multibit.exchange.infrastructure.adaptor.api.resources.SecurityDescriptor;

import static org.fest.assertions.api.Assertions.assertThat;

public class SecurityDescriptorSerializationTest extends BaseDropWizardSerializationTest {

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Test
  public void testDeserializationFromJson_validJson() throws Exception {
    // Arrange
    String expectedTicker = "BTC/CAD";
    String expectedBaseCurrency = "BTC";
    String expectedCounterCurrency = "CAD";
    String json =
        "{" +
            "\"ticker\":\"" + expectedTicker + "\", " +
            "\"baseCurrency\":\"" + expectedBaseCurrency + "\", " +
            "\"counterCurrency\":\"" + expectedCounterCurrency + "\"" +
            "}";

    // Act
    final SecurityDescriptor deserializedObject =
        (SecurityDescriptor) deserializeFromJson(json, SecurityDescriptor.class);

    // Assert
    assertThat(deserializedObject.getTicker()).isEqualTo(expectedTicker);
    assertThat(deserializedObject.getBaseCurrency()).isEqualTo(expectedBaseCurrency);
    assertThat(deserializedObject.getCounterCurrency()).isEqualTo(expectedCounterCurrency);
  }

  @Test
  public void testSecurityDescriptorDeserialization_validJson_extraField() throws Exception {
    // Arrange
    String expectedTicker = "BTC/CAD";
    String expectedBaseCurrency = "BTC";
    String expectedCounterCurrency = "CAD";
    String json =
        "{" +
            "\"ticker\":\"" + expectedTicker + "\", " +
            "\"baseCurrency\":\"" + expectedBaseCurrency + "\", " +
            "\"counterCurrency\":\"" + expectedCounterCurrency + "\", " +
            "\"extraField\":\"extraFieldValue\"" +
            "}";

    // Act
    final SecurityDescriptor deserializedObject =
        (SecurityDescriptor) deserializeFromJson(json, SecurityDescriptor.class);

    // Assert
    assertThat(deserializedObject.getTicker()).isEqualTo(expectedTicker);
    assertThat(deserializedObject.getBaseCurrency()).isEqualTo(expectedBaseCurrency);
    assertThat(deserializedObject.getCounterCurrency()).isEqualTo(expectedCounterCurrency);
  }

  @Test
  public void testSecurityDescriptorDeserialization_invalidJson_symbols() throws Exception {
    // Arrange
    String expectedTicker = "BTC/CAD";
    String expectedBaseCurrency = "BTC";
    String expectedCounterCurrency = "CAD";
    String json =
        "{" +
            "\"intentionalTypoInFieldName_tickerSymbol\":\"" + expectedTicker + "\", " +
            "\"baseCurrency\":\"" + expectedBaseCurrency + "\", " +
            "\"counterCurrency\":\"" + expectedCounterCurrency + "\"" +
            "}";
    thrown.expect(JsonMappingException.class);
    thrown.expectMessage("ticker must not be null or empty: 'null");

    // Act
    deserializeFromJson(json, SecurityDescriptor.class);
  }
}
