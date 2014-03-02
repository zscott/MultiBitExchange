Feature: The quote for a currency pair is updated as the orderbook changes.

  Background: Setup the currency pair, and initial orders
    Given the following orders are submitted:
      | Broker | Side | Qty | Price |
      | A      | Buy  | 100 | 5.87  |
      | B      | Sell | 1   | 6.25  |
    Then no trades are generated
    And market order book looks like:
      | Broker | Qty | Price | Price | Qty | Broker |
      | A      | 100 | 5.87  | 6.25  | 1   | B      |
    And the quote looks like:
      | Bid  | Ask  | Spread |
      | 5.87 | 6.25 | 0.38   |

  Scenario: Adding a sell that is higher than best sell doesn't affect the quote.
    When the following orders are submitted:
      | Broker | Side | Qty | Price |
      | C      | Sell | 100 | 7.25  |
    Then no trades are generated
    And market order book looks like:
      | Broker | Qty | Price | Price | Qty | Broker |
      | A      | 100 | 5.87  | 6.25  | 1   | B      |
      |        |     |       | 7.25  | 100 | C      |
    And the quote looks like:
      | Bid  | Ask  | Spread |
      | 5.87 | 6.25 | 0.38   |

  Scenario: Adding a sell that is lower than best sell affects the quote.
    When the following orders are submitted:
      | Broker | Side | Qty | Price      |
      | D      | Sell | 100 | 6.24999999 |
    Then no trades are generated
    And market order book looks like:
      | Broker | Qty | Price | Price      | Qty | Broker |
      | A      | 100 | 5.87  | 6.24999999 | 100 | D      |
      |        |     |       | 6.25       | 1   | B      |
    And the quote looks like:
      | Bid  | Ask        | Spread     |
      | 5.87 | 6.24999999 | 0.37999999 |

  Scenario: Adding a sell that crosses the book and is traded immediately doesn't affect the quote.
    When the following orders are submitted:
      | Broker | Side | Qty | Price |
      | D      | Sell | 60  | 5.865 |
    Then the following trades are generated:
      | Buying broker | Selling broker | Qty | Price |
      | A             | D              | 60  | 5.87  |
    And market order book looks like:
      | Broker | Qty | Price | Price | Qty | Broker |
      | A      | 40  | 5.87  | 6.25  | 1   | B      |
    And the quote looks like:
      | Bid  | Ask  | Spread |
      | 5.87 | 6.25 | 0.38   |
