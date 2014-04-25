# Core Domain Model

## Terms

### Asset
An asset is something that has value and can be bought and sold on a market. An asset is identified by it's ticker symbol.
A currency pair such as BTC/USD is an example of an asset that represents the value of a single BTC denominated in USD. When
a buy order is placed for BTC/USD the buyer must have enough USD to cover the purchase. When sellers are found the order
will result in one or more transactions with the buyer receiving BTC and the seller receiving USD.

An asset therefore has a settlement currency (which itself can be an Asset). For example it's technically possible
to have an asset that represents BTC denominated in LTC/USD. When a buy order is placed for BTC/(LTC/USD) the buyer
buyer must have enough LTC/USD on hand to cover the purchase. What does this mean? The buyer must be able to



Exchange [1] ---> [0..*] AssetPair
- assets: Map<String,AssetPair>


CurrencyPair [1]
- symbol: String
- baseCurrency: Currency
- counterCurrency: Currency
- matchingStrategy: MatchingStrategy
- buyBook: OrderBook
- sellBook: OrderBook


Currency
- symbol


Party [1] ---> [0..*] Portfolio
- kycStuff: String


Portfolio [1] ---> [0..*] Account


Account
- denomination: Asset
- balance: BigDecimal


