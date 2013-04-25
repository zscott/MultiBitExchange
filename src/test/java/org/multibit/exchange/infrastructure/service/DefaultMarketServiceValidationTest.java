package org.multibit.exchange.infrastructure.service;

import org.junit.Test;

/**
 * ValidationTests dealing with the adding a new resources.
 *
 * @since 0.0.1
 *         
 */
public class DefaultMarketServiceValidationTest extends DefaultMarketServiceTest {

  @Test
  public void testAddMarket_validDescriptor() {
    // Arrange
    String symbol = "multibitCAD";
    String itemSymbol = "BTC";
    String currencySymbol = "CAD";

    // Act
    marketService.addMarket(symbol, itemSymbol, currencySymbol);
  }

  @Test
  public void testAddMarket_duplicateSymbol() {
    // Arrange
    String symbol = "multibitCAD";
    String itemSymbol = "BTC";
    String currencySymbol = "CAD";
    marketService.addMarket(symbol, itemSymbol, currencySymbol);
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("resources already exists: multibitCAD");

    // Act
    marketService.addMarket(symbol, itemSymbol, currencySymbol);
  }

  @Test
  public void testAddMarket_nullSymbol() {
    // Arrange
    String symbol = null;
    String itemSymbol = "BTC";
    String currencySymbol = "CAD";
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("market symbol must not be null or empty: 'null");

    // Act
    marketService.addMarket(symbol, itemSymbol, currencySymbol);
  }

  @Test
  public void testAddMarket_emptySymbol() {
    // Arrange
    String symbol = "";
    String itemSymbol = "BTC";
    String currencySymbol = "CAD";
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("market symbol must not be null or empty: ''");

    // Act
    marketService.addMarket(symbol, itemSymbol, currencySymbol);
  }

  @Test
  public void testAddMarket_nullItemSymbol() {
    // Arrange
    String symbol = "multibitCAD";
    String itemSymbol = null;
    String currencySymbol = "CAD";
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("item symbol must not be null or empty: 'null'");

    // Act
    marketService.addMarket(symbol, itemSymbol, currencySymbol);
  }

  @Test
  public void testAddMarket_emptyItemSymbol() {
    // Arrange
    String symbol = "multibitCAD";
    String itemSymbol = "";
    String currencySymbol = "CAD";
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("item symbol must not be null or empty: ''");

    // Act
    marketService.addMarket(symbol, itemSymbol, currencySymbol);
  }

  @Test
  public void testAddMarket_nullCurrencySymbol() {
    // Arrange
    String symbol = "multibitCAD";
    String itemSymbol = "BTC";
    String currencySymbol = null;
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("currency symbol must not be null or empty: 'null'");

    // Act
    marketService.addMarket(symbol, itemSymbol, currencySymbol);
  }

  @Test
  public void testAddMarket_emptyCurrencySymbol() {
    // Arrange
    String symbol = "multibitCAD";
    String itemSymbol = "BTC";
    String currencySymbol = null;
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("currency symbol must not be null or empty: 'null'");

    // Act
    marketService.addMarket(symbol, itemSymbol, currencySymbol);
  }

}
