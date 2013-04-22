package com.blurtty.peregrine.service;

import org.junit.Test;

import com.blurtty.peregrine.infrastructure.service.DefaultMarketServiceTest;

/**
 * ValidationTests dealing with the adding a new resources.
 *
 * @since 0.0.1
 *         
 */
public class CreateMarketValidationTests extends DefaultMarketServiceTest {

  @Test
  public void testCreateMarket_validDescriptor() {
    // Arrange
    String symbol = "peregrineCAD";
    String itemSymbol = "BTC";
    String currencySymbol = "CAD";

    // Act
    appService.addMarket(symbol, itemSymbol, currencySymbol);
  }

  @Test
  public void testCreateMarket_duplicateSymbol() {
    // Arrange
    String symbol = "peregrineCAD";
    String itemSymbol = "BTC";
    String currencySymbol = "CAD";
    appService.addMarket(symbol, itemSymbol, currencySymbol);
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("resources already exists: peregrineCAD");

    // Act
    appService.addMarket(symbol, itemSymbol, currencySymbol);
  }

  @Test
  public void testCreateMarket_nullSymbol() {
    // Arrange
    String symbol = null;
    String itemSymbol = "BTC";
    String currencySymbol = "CAD";
    thrown.expect(NullPointerException.class);
    thrown.expectMessage("resources symbol must not be null or empty: 'null");

    // Act
    appService.addMarket(symbol, itemSymbol, currencySymbol);
  }

  @Test
  public void testCreateMarket_emptySymbol() {
    // Arrange
    String symbol = "";
    String itemSymbol = "BTC";
    String currencySymbol = "CAD";
    thrown.expect(NullPointerException.class);
    thrown.expectMessage("resources symbol must not be null or empty: ''");

    // Act
    appService.addMarket(symbol, itemSymbol, currencySymbol);
  }

  @Test
  public void testCreateMarket_nullItemSymbol() {
    // Arrange
    String symbol = "peregrineCAD";
    String itemSymbol = null;
    String currencySymbol = "CAD";
    thrown.expect(NullPointerException.class);
    thrown.expectMessage("item symbol must not be null or empty: 'null'");

    // Act
    appService.addMarket(symbol, itemSymbol, currencySymbol);
  }

  @Test
  public void testCreateMarket_emptyItemSymbol() {
    // Arrange
    String symbol = "peregrineCAD";
    String itemSymbol = "";
    String currencySymbol = "CAD";
    thrown.expect(NullPointerException.class);
    thrown.expectMessage("item symbol must not be null or empty: ''");

    // Act
    appService.addMarket(symbol, itemSymbol, currencySymbol);
  }

  @Test
  public void testCreateMarket_nullCurrencySymbol() {
    // Arrange
    String symbol = "peregrineCAD";
    String itemSymbol = "BTC";
    String currencySymbol = null;
    thrown.expect(NullPointerException.class);
    thrown.expectMessage("currency symbol must not be null or empty: 'null'");

    // Act
    appService.addMarket(symbol, itemSymbol, currencySymbol);
  }

  @Test
  public void testCreateMarket_emptyCurrencySymbol() {
    // Arrange
    String symbol = "peregrineCAD";
    String itemSymbol = "BTC";
    String currencySymbol = null;
    thrown.expect(NullPointerException.class);
    thrown.expectMessage("currency symbol must not be null or empty: ''");

    // Act
    appService.addMarket(symbol, itemSymbol, currencySymbol);
  }

}
