Feature: The quote for a currency pair is updated as the orderbook changes.

  Background: Setup the currency pair, and initial orders
    Given the following orders are submitted:
      | Broker | Side | Qty | Price |
      | A      | Buy  | 100 | 5.87  |
      | B      | Sell | 10  | 6.25  |
    Then no trades are generated
    And market order book looks like:
      | Broker | Qty | Price | Price | Qty | Broker |
      | A      | 100 | 5.87  | 6.25  | 10  | B      |
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
      | A      | 100 | 5.87  | 6.25  | 10  | B      |
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
      |        |     |       | 6.25       | 10  | B      |
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
      | A      | 40  | 5.87  | 6.25  | 10  | B      |
    And the quote looks like:
      | Bid  | Ask  | Spread |
      | 5.87 | 6.25 | 0.38   |

  Scenario Outline: Adding a sell order below the best ask and above the best bid will affect the quote.
    When the following orders are submitted:
      | Broker | Side | Qty | Price        |
      | XX     | Sell | 867 | <Order Price> |
    Then market order book looks like:
      | Broker | Qty | Price | Price        | Qty | Broker |
      | A      | 100 | 5.87  | <Mkt Price> | 867 | XX     |
      |        |     |       | 6.25         | 10  | B      |
    And the quote looks like:
      | Bid  | Ask   | Spread   |
      | 5.87 | <Ask> | <Spread> |

  Examples:
    | Order Price | Mkt Price   | Ask        | Spread     |
    | 6.24        | 6.24        | 6.24       | 0.37       |
    | 6.23        | 6.23        | 6.23       | 0.36       |
    | 6.22        | 6.22        | 6.22       | 0.35       |
    | 6.00        | 6           | 6          | 0.13       |
    | 5.89        | 5.89        | 5.89       | 0.02       |
    | 5.88        | 5.88        | 5.88       | 0.01       |
    | 5.871       | 5.871       | 5.871      | 0.001      |
    | 5.8701      | 5.8701      | 5.8701     | 0.0001     |
    | 5.87001     | 5.87001     | 5.87001    | 0.00001    |
    | 5.870001    | 5.870001    | 5.870001   | 0.000001   |
    | 5.8700001   | 5.8700001   | 5.8700001  | 0.0000001  |
    | 5.87000001  | 5.87000001  | 5.87000001 | 0.00000001 |

  Scenario Outline: Adding a small sell order that crosses with the best bid will trade immediately and therefore not affect the quote.
    When the following orders are submitted:
      | Broker | Side | Qty | Price        |
      | XX     | Sell | 1   | <Sell Price> |
    Then market order book looks like:
      | Broker | Qty | Price | Price | Qty | Broker |
      | A      | 99  | 5.87  | 6.25  | 10  | B      |
    And the quote looks like:
      | Bid  | Ask   | Spread   |
      | 5.87 | <Ask> | <Spread> |

  Examples:
    | Sell Price | Ask  | Spread |
    | 5.87       | 6.25 | 0.38   |
    | 5.86       | 6.25 | 0.38   |
    | 5.85       | 6.25 | 0.38   |
    | 5.84       | 6.25 | 0.38   |
    | 5.83       | 6.25 | 0.38   |
    | 5.82       | 6.25 | 0.38   |
    | 5.81       | 6.25 | 0.38   |
    | 5.80       | 6.25 | 0.38   |
    | 1.00       | 6.25 | 0.38   |
    | 0.10       | 6.25 | 0.38   |
    | 0.01       | 6.25 | 0.38   |
    | 0.00000001 | 6.25 | 0.38   |

  Scenario Outline: Adding a buy order above the best bid and below the best ask will affect the quote.
    When the following orders are submitted:
      | Broker | Side | Qty | Price       |
      | YY     | BUY  | 867 | <Buy Price> |
    Then market order book looks like:
      | Broker | Qty | Price       | Price | Qty | Broker |
      | YY     | 867 | <Buy Price> | 6.25  | 10  | B      |
      | A      | 100 | 5.87        |       |     |        |
    And the quote looks like:
      | Bid   | Ask  | Spread   |
      | <Bid> | 6.25 | <Spread> |

  Examples:
    | Buy Price | Bid  | Spread |
    | 5.88      | 5.88 | 0.37   |
    | 5.98      | 5.98 | 0.27   |
    | 6.08      | 6.08 | 0.17   |
    | 6.24      | 6.24 | 0.01   |

  Scenario Outline: Adding a small buy order that crosses with the best ask will trade immediately and therefore not affect the quote.
    When the following orders are submitted:
      | Broker | Side | Qty | Price       |
      | YY     | BUY  | 1   | <Buy Price> |
    Then market order book looks like:
      | Broker | Qty | Price | Price | Qty | Broker |
      | A      | 100 | 5.87  | 6.25  | 9   | B      |
    And the quote looks like:
      | Bid   | Ask  | Spread   |
      | <Bid> | 6.25 | <Spread> |

  Examples:
    | Buy Price | Bid  | Spread |
    | 6.25      | 5.87 | 0.38   |
    | 6.26      | 5.87 | 0.38   |
    | 6.27      | 5.87 | 0.38   |
    | 60.25     | 5.87 | 0.38   |
    | 600       | 5.87 | 0.38   |
