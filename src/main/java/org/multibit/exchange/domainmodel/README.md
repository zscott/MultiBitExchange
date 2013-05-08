Domain Model Terminology

Account - An account held by a trader denominated in some tradeable item. The denomination could be CAD, USD, BTC, LTC, or anything else
that can be used to buy and sell other tradeable items.

TradeableItem - A tradeable item is something that can be exchanged for another tradeable item at a given price.

TradeablePair - A pair of types of tradeable items with it's own orderbook. Units of one item in the tradeable pair can be
traded for units of the other through bid and ask orders.

Security - A security comprises a trading symbol a tradeable pair. It is the thing that scopes all trade orders
and executions.

