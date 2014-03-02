Feature: Core matching logic for market orders
 
  Scenario: Matching a large Sell market order against multiple limit orders
  The order is large enough to fill the entire opposite book
  The unfilled portion of the market order is cancelled
    When the following orders are submitted:
      | Broker | Side | Qty | Price |
      | A      | Buy  | 100 | 10.7  |
      | B      | Buy  | 200 | 10.6  |
      | C      | Buy  | 300 | 10.5  |
      | D      | Sell | 650 | M     |
    Then the following trades are generated:
      | Buying broker | Selling broker | Qty | Price |
      | A             | D              | 100 | 10.7  |
      | B             | D              | 200 | 10.6  |
      | C             | D              | 300 | 10.5  |
    And market order book looks like:
      | Broker | Qty | Price | Price | Qty | Broker |
      |        |     |        |       |      |        |
 
  Scenario: Matching a small Sell market order against limit orders
  The order is small enough so it fills entirely
    When the following orders are submitted:
      | Broker | Side | Qty | Price |
      | A      | Buy  | 100 | 10.7  |
      | B      | Buy  | 200 | 10.6  |
      | C      | Buy  | 300 | 10.5  |
      | D      | Sell | 150 | M     |
    Then the following trades are generated:
      | Buying broker | Selling broker | Qty | Price |
      | A             | D              | 100 | 10.7  |
      | B             | D              | 50  | 10.6  |
    And market order book looks like:
      | Broker | Qty | Price | Price | Qty | Broker |
      | B      | 150 | 10.6  |       |     |        |
      | C      | 300 | 10.5  |       |     |        |
 
  Scenario: Matching a large Buy market order against multiple limit orders
  The order is large enough to fill the entire opposite book
  The unfilled portion of the market order is cancelled
    When the following orders are submitted:
      | Broker | Side | Qty | Price |
      | A      | Sell | 100 | 10.5  |
      | B      | Sell | 200 | 10.6  |
      | C      | Sell | 300 | 10.7  |
      | D      | Buy  | 650 | M     |
    Then the following trades are generated:
      | Buying broker | Selling broker | Qty | Price |
      | D             | A              | 100 | 10.5  |
      | D             | B              | 200 | 10.6  |
      | D             | C              | 300 | 10.7  |
    And market order book looks like:
      | Broker | Qty | Price | Price | Qty | Broker |
      |        |      |       |       |      |        |
 
  Scenario: Matching a small Buy market order against limit orders
  The order is small enough so it fills entirely
    When the following orders are submitted:
      | Broker | Side | Qty | Price |
      | A      | Sell | 100 | 10.5  |
      | B      | Sell | 200 | 10.6  |
      | C      | Sell | 300 | 10.7  |
      | D      | Buy  | 150 | M     |
    Then the following trades are generated:
      | Buying broker | Selling broker | Qty | Price |
      | D             | A              | 100 | 10.5  |
      | D             | B              | 50  | 10.6  |
    And market order book looks like:
      | Broker | Qty | Price | Price | Qty | Broker |
      |        |     |       | 10.6  | 150 | B      |
      |        |     |       | 10.7  | 300 | C      |