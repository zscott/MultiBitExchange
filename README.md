[![Ready Issues](https://badge.waffle.io/zscott/MultiBitExchange.png?label=ready)](https://waffle.io/zscott/MultiBitExchange)

[![Build Status](https://travis-ci.org/zscott/MultiBitExchange.png)](https://travis-ci.org/zscott/MultiBitExchange)

## The Vision

MultiBit Exchange is an exchange platform that can be leveraged as a starting point for creating an exchange of any
kind.

## What is it?

MultiBit Exchange is a platform that consists of a REST API front-end and trading engine back-end. It also aims to be
well tested and clean making it easier to understand and extend.

## What can I do with it?

There is an endless variety of types of exchanges that can be created using MultiBit Exchange. Here are a few ideas:
* An exchange for trading fiat for electronic currencies such as Bitcoin, Litecoin, Dogecoin, and Namecoin.
* An exchange for trading Bitcoin for cell phone minutes, gift cards, gold, etc.
* A precious metal exchange: gold, silver, platinum, etc.
* A traditional currency exchange
* A test platform for experimenting with trading bots, experimental order types, etc.
* An Inter-exchange arbitrage platform

## What are the major characteristics of MultiBit Exchange?

MultiBit Exchange aims to be more that just a functioning exchange, but a well crafted codebase that is:
* Tested
* Neatly structured
* High-throughput / Low-latency by leveraging event sourcing and the [LMAX Disruptor pattern](http://martinfowler.com/articles/lmax.html).
* Extensible (not necessarily configurable or pluggable, but definitely malleable)

## What development methodology is used?

> "Clean code always looks like it was written by someone who cares."
> Michael Feathers

Great software doesn't just happen. It requires a disciplined approach. The following guiding principles are
used to develop MultiBit Exchange:

* Focus on the core domain and use of Domain Driven Design principles
* Create tests to guide development - Test Driven Development
* Be disciplined and keep the code well-structured and layered
* Don't re-invent the wheel, but leverage proven technology wherever possible

## Architecture

MultiBit Exchange follows the hexagonal architecture. See: http://alistair.cockburn.us/Hexagonal+architecture
TL;DR - Allow everything to depend on the abstractions such as the Core domain. Don't allow any of the abstractions
depend on the infrastructure. This keeps the separation between what the system does decoupled from how.

## Initial goals of MultiBit Exchange

The initial focus is to release a robust Matching Engine. Following that, there are many directions MultiBit Exchange
can be taken.

## Future Ideas for MultiBit Exchange

MultiBit Exchange is relatively new, so focus is key, but I have a few ideas about where to go after the first release:

* Accounting & Inter-Broker settlement - include more support for accounting functions
* Decentralization - not sure how yet, but being resistant to shutdown and DDoS attacks is important.
* Enhance Performance - to be able to handle huge volumes without compromising on latency.

## Standing on the Shoulders of Giants

Many thanks to all the hard work that was put into the many ideas, libraries, and systems that MultiBit Exchange
is built on:

Domain Driven Design lies at the core of MultiBit Exchange.
http://skillsmatter.com/expert-profile/home/eric-evans - Thank you Eric Evans.

google-guice - Guice is used for dependency injection throughout.
https://code.google.com/p/google-guice/people/list

guava-libraries - Guava is used throughout and makes Java just a little nicer to work with.
https://code.google.com/p/guava-libraries/people/list

Dropwizard - Dropwizard serves as the front-end for REST API and web interfaces.
http://dropwizard.codahale.com/about/contributors/

LMAX Disruptor - The Disruptor pattern is used to help with speedy production and consumption of events.
https://github.com/mikeb01

AXON Framework - The AXON Framework is used to help with CQRS.
http://www.axonframework.org/

MongoDB - Much of the persistence is provided by MongoDB.
http://www.mongodb.org/

Heroku - Used as a hosting environment during development.
https://www.heroku.com/

## Installing MongoDB

MongoDB is used as an Event Store and to persist CQRS [Read Models](http://martinfowler.com/bliki/CQRS.html).
Follow the usual [MongoDB installation instructions](http://docs.mongodb.org/manual/installation/), such as

```
$ brew update
$ brew install mongo
```

Start MongoDB as a background process with

```
$ mongod &
```

Then create the following collections through the Mongo CLI

```
$ mongo
> use mbexchange
```

## Building and running

From the console you can do the following
```
$ cd <project root>
$ mvn clean install
$ java -jar target/web-develop-SNAPSHOT.jar server mbexchange-demo.yml
```

If startup was successful, the first thing you will need to do is create an exchange (a container for securities).
Using a browser plugin like [POSTMAN](https://chrome.google.com/webstore/detail/postman-rest-client/fdmmgilgnpjigdojojpjoooidkmcomcm?hl=en)
POST a JSON document to [localhost:8080/exchanges](http://localhost:8080/exchanges)

Be sure to include the following header
```
Content-type: application/json
```

The format of the JSON document for creating an exchange:
```
{
  "identifier":"myexchange"
}
```

Next, you can navigate to
[localhost:8080/exchanges/myexchange/securities](http://localhost:8080/exchanges/myexchange/securities) to see a list
of securities in your exchange.

To add a security to your exchange
POST a JSON document to [/exchanges/myexchange/securities](http://localhost:8080/exchanges/myexchange/securities)

Again, be sure to include the following header
```
Content-type: application/json
```

The OLD format of the JSON document for creating securities:
```
{
  "tickerSymbol": "TICKER",
  "itemSymbol": "ITM",
  "currencySymbol": "CUR"
}
```


The CURRENT format of the JSON document for creating securities:
```
{
  "ticker": "TICKER",
  "baseCurrency": "BaseCCY",
  "counterCurrency": "CounterCCY"
}
```


Navigate back to [localhost:8080/exchanges/myexchange/securities](http://localhost:8080/exchanges/myexchange/securities) to
see the newly created security.

To submit a market order to your exchange
POST a JSON document to [/exchanges/myexchange/securities](http://localhost:8080/exchanges/myexchange/orders)

Again, be sure to include the following header
```
Content-type: application/json
```

The format of the JSON document for specifying orders:
 ```
 {
   "broker":"BrokerIdentifier",
   "side":"Sell",
   "qty":"80.33001",
   "ticker":"TICKER",
   "price":"M"
 }
 ```
* "broker" can be any string currently.
* "side" is case-insensitive and can be "Buy" or "Sell"
* "qty" is a number between 0 and 10,000,000 with a maximum of 8 decimal places.
* "ticker" must correspond to a previously added security (see above)
* "price" can be "M" for market orders or a number representing limit price for limit orders.




## How will I know if the server is working?
On startup you should see the following:
```
INFO  [2014-01-25 18:12:40,007] com.yammer.dropwizard.cli.ServerCommand: Starting MultiBitExchangeApiWebService
___  ___      _ _   _______ _ _     _____         _                            
|  \/  |     | | | (_) ___ (_) |   |  ___|       | |                           
| .  . |_   _| | |_ _| |_/ /_| |_  | |____  _____| |__   __ _ _ __   __ _  ___ 
| |\/| | | | | | __| | ___ \ | __| |  __\ \/ / __| '_ \ / _` | '_ \ / _` |/ _ \
| |  | | |_| | | |_| | |_/ / | |_  | |___>  < (__| | | | (_| | | | | (_| |  __/
\_|  |_/\__,_|_|\__|_\____/|_|\__| \____/_/\_\___|_| |_|\__,_|_| |_|\__, |\___|
                                                                     __/ |     
                                                                    |___/


INFO  [2014-01-25 18:12:40,183] com.yammer.dropwizard.config.Environment: 

    GET     /exchanges/{exchangeId}/securities (org.multibit.exchange.infrastructure.adaptor.api.resources.SecuritiesResource)
    GET     /exchanges/{exchangeId}/securities/{ticker}/orderbook (org.multibit.exchange.infrastructure.adaptor.api.resources.SecuritiesResource)
    POST    /exchanges/{exchangeId}/securities (org.multibit.exchange.infrastructure.adaptor.api.resources.SecuritiesResource)
    POST    /exchanges (org.multibit.exchange.infrastructure.adaptor.api.resources.ExchangeResource)
    POST    /exchanges/{exchangeId}/orders (org.multibit.exchange.infrastructure.adaptor.api.resources.ExchangeResource)

INFO  [2014-01-25 18:12:40,183] com.yammer.dropwizard.config.Environment: tasks = 

    POST    /tasks/gc (com.yammer.dropwizard.tasks.GarbageCollectionTask)
```
## How was this startup banner generated?
http://patorjk.com/software/taag/#p=display&f=Doom&t=MultiBit%20Exchange

## Which branch?
Use `master` for the latest production release. Use `develop` for the latest release candidate.

If you wish to contribute, please start with `develop`.

## License

Copyright (c) 2014 Zach Scott, http://zach-scott.com/

This software consists of voluntary contributions made by many
individuals (see AUTHORS.txt) For the exact contribution history,
see the revision history and logs, available at
https://github.com/zscott/MultiBitExchange

Permission is hereby granted, free of charge, to any person obtaining
a copy of this software and associated documentation files (the
"Software"), to deal in the Software without restriction, including
without limitation the rights to use, copy, modify, merge, publish,
distribute, sublicense, and/or sell copies of the Software, and to
permit persons to whom the Software is furnished to do so, subject to
the following conditions:

The above copyright notice and this permission notice shall be
included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.


