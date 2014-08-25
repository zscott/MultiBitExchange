# Design guidelines

## Domain Model
* With commands, where do primitives get mapped to domain objects? Service Layer? Command Handler?

# Trade-offs / decisions
1) Only support currencies at this point. Don't worry about trading coffee beans for snickers bars.

# Refactorings

1) Replace RegisterTickerCommand with RegisterCurrencyPair command.
* [DONE] find and update all callers to new RegisterCurrencyPairCommand - compile, commit
* [DONE] update TickerRegisteredEvent to CurrencyPairRegisteredEvent
* [DONE] update TickerRemovedEvent
* [DONE] update RemoveTickerCommand

2) [DONE] Introduce CurrencyPairId

3) [DONE] Introduce CurrencyId and use in place of Currency in commands and events.

4) Find and replace all instances of Ticker or ticker with currency pair symbol

5) [DONE] Find and replace all instances of Security with CurrencyPair

6) Trade must include baseCurrency and counterCurrency

7) Remove atmosphere (first version of REST API requires polling) - build separate app for atmosphere later.

8) Choose one: ReadModel, PresentationModel, QueryModel
