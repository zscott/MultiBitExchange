package org.multibit.exchange.infrastructure.adaptor.web.integration;

import com.fasterxml.jackson.databind.JsonMappingException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.multibit.exchange.infrastructure.adaptor.web.restapi.resources.CurrencyPairDescriptor;

import static org.fest.assertions.api.Assertions.assertThat;

public class CurrencyPairDescriptorSerializationTest extends BaseDropWizardSerializationTest {

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Test
  public void testDeserializationFromJson_validJson() throws Exception {
    // Arrange
    String expectedBaseCurrency = "BTC";
    String expectedCounterCurrency = "CAD";
    String json =
            "{" +
                    "\"baseCurrency\":\"" + expectedBaseCurrency + "\", " +
                    "\"counterCurrency\":\"" + expectedCounterCurrency + "\"" +
                    "}";

    // Act
    final CurrencyPairDescriptor deserializedObject =
            (CurrencyPairDescriptor) deserializeFromJson(json, CurrencyPairDescriptor.class);

    // Assert
    assertThat(deserializedObject.getBaseCurrency()).isEqualTo(expectedBaseCurrency);
    assertThat(deserializedObject.getCounterCurrency()).isEqualTo(expectedCounterCurrency);
  }

  @Test
  public void testSecurityDescriptorDeserialization_validJson_extraField() throws Exception {
    // Arrange
    String expectedBaseCurrency = "BTC";
    String expectedCounterCurrency = "CAD";
    String json =
            "{" +
                    "\"baseCurrency\":\"" + expectedBaseCurrency + "\", " +
                    "\"counterCurrency\":\"" + expectedCounterCurrency + "\", " +
                    "\"extraField\":\"extraFieldValue\"" +
                    "}";

    // Act
    final CurrencyPairDescriptor deserializedObject =
            (CurrencyPairDescriptor) deserializeFromJson(json, CurrencyPairDescriptor.class);

    // Assert
    assertThat(deserializedObject.getBaseCurrency()).isEqualTo(expectedBaseCurrency);
    assertThat(deserializedObject.getCounterCurrency()).isEqualTo(expectedCounterCurrency);
  }

  @Test
  public void testSecurityDescriptorDeserialization_baseCurrencyTypo() throws Exception {
    // Arrange
    String expectedBaseCurrency = "BTC";
    String expectedCounterCurrency = "CAD";
    String json =
            "{" +
                "\"intentionalTypoInFieldName_baseCurrency\":\"" + expectedBaseCurrency + "\", " +
                "\"counterCurrency\":\"" + expectedCounterCurrency + "\"" +
                    "}";
    thrown.expect(JsonMappingException.class);
    thrown.expectMessage("baseCurrency must not be null or empty: 'null");

    // Act
    deserializeFromJson(json, CurrencyPairDescriptor.class);
  }

  @Test
  public void testSecurityDescriptorDeserialization_counterCurrencyTypo() throws Exception {
    // Arrange
    String expectedBaseCurrency = "BTC";
    String expectedCounterCurrency = "CAD";
    String json =
        "{" +
            "\"baseCurrency\":\"" + expectedBaseCurrency + "\", " +
            "\"intentionalTypoInFieldName_counterCurrency\":\"" + expectedCounterCurrency + "\"" +
            "}";
    thrown.expect(JsonMappingException.class);
    thrown.expectMessage("counterCurrency must not be null or empty: 'null");

    // Act
    deserializeFromJson(json, CurrencyPairDescriptor.class);
  }
}
