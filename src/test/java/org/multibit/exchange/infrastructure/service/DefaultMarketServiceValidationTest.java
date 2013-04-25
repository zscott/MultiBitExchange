package org.multibit.exchange.infrastructure.service;

import org.junit.Test;

/**
 * ValidationTests dealing with the adding a new market.
 *
 * @since 0.0.1
 *         
 */
public class DefaultMarketServiceValidationTest extends DefaultMarketServiceTest {

  @Test
  public void testAddMarket_validDescriptor() {
    // Arrange
    String marketSymbol = "multibitCAD";
    String itemSymbol = "BTC";
    String currencySymbol = "CAD";

    // Act
    marketService.addMarket(marketSymbol, itemSymbol, currencySymbol);
  }

  @Test
  public void testAddMarket_duplicateSymbol() {
    // Arrange
    String marketSymbol = "multibitCAD";
    String itemSymbol = "BTC";
    String currencySymbol = "CAD";
    marketService.addMarket(marketSymbol, itemSymbol, currencySymbol);
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("market already exists: multibitCAD");

    // Act
    marketService.addMarket(marketSymbol, itemSymbol, currencySymbol);
  }

  @Test
  public void testAddMarket_nullSymbol() {
    // Arrange
    String marketSymbol = null;
    String itemSymbol = "BTC";
    String currencySymbol = "CAD";
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("marketSymbol must not be null or empty: 'null");

    // Act
    marketService.addMarket(marketSymbol, itemSymbol, currencySymbol);
  }

  @Test
  public void testAddMarket_emptySymbol() {
    // Arrange
    String marketSymbol = "";
    String itemSymbol = "BTC";
    String currencySymbol = "CAD";
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("marketSymbol must not be null or empty: ''");

    // Act
    marketService.addMarket(marketSymbol, itemSymbol, currencySymbol);
  }

  @Test
  public void testAddMarket_nullItemSymbol() {
    // Arrange
    String marketSymbol = "multibitCAD";
    String itemSymbol = null;
    String currencySymbol = "CAD";
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("itemSymbol must not be null or empty: 'null'");

    // Act
    marketService.addMarket(marketSymbol, itemSymbol, currencySymbol);
  }

  @Test
  public void testAddMarket_emptyItemSymbol() {
    // Arrange
    String marketSymbol = "multibitCAD";
    String itemSymbol = "";
    String currencySymbol = "CAD";
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("itemSymbol must not be null or empty: ''");

    // Act
    marketService.addMarket(marketSymbol, itemSymbol, currencySymbol);
  }

  @Test
  public void testAddMarket_nullCurrencySymbol() {
    // Arrange
    String marketSymbol = "multibitCAD";
    String itemSymbol = "BTC";
    String currencySymbol = null;
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("currencySymbol must not be null or empty: 'null'");

    // Act
    marketService.addMarket(marketSymbol, itemSymbol, currencySymbol);
  }

  @Test
  public void testAddMarket_emptyCurrencySymbol() {
    // Arrange
    String marketSymbol = "multibitCAD";
    String itemSymbol = "BTC";
    String currencySymbol = null;
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("currencySymbol must not be null or empty: 'null'");

    // Act
    marketService.addMarket(marketSymbol, itemSymbol, currencySymbol);
  }

}
