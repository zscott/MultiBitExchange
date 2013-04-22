# The Peregrine Exchange Platform

## Overview

The Peregrine Exchange Platform aims to be the fastest and most robust open source exchange platform on the planet.

Peregrine is not a currency trading company. The focus of this project is to create a platform that can be used by
any individual, group, or company that wishes to build on it.

## Development Methodology

> "Clean code always looks like it was written by someone who cares."
> Michael Feathers

Great software doesn't just happen. It requires a  disciplined approach. The following methodology is
used to develop Peregrine:

Focus on the core domain and apply Domain Driven Design principles.

Apply Test Driven Development.

Keep the code well structured and clean.

Stand on the shoulders of giants by using existing technology where available.

## Architecture

Use the hexagonal architecture is used: http://alistair.cockburn.us/Hexagonal+architecture

REST is favored for external interfaces.

## Goals of the Project

# Create a decentralize platform that is resistant to shutdown and DDOS attacks.

# Handle very very large trading volumes with extremely low latency.

# Make it simple to use and easy to leverage.

# Provide support for any currency virtual or non-virtual.

## Standing on the Shoulders of Giants

Many thanks to all the hard work that was put into the many ideas, libraries, and systems that Peregrine is built on:

# Domain Driven Design is the very foundation of Peregrine. This is one of the most important contributions
to the craft of software development.
Thank you Eric Evans! (http://skillsmatter.com/expert-profile/home/eric-evans)

# google-guice - Guice is used for dependency injection throughout.
Thank you! https://code.google.com/p/google-guice/people/list

# guava-libraries - Guava is used throughout and makes java just a little nicer to use.
Thank you! https://code.google.com/p/guava-libraries/people/list

# Dropwizard - Dropwizard serves as the front-end for REST and Web UI interfaces.
Thank you! http://dropwizard.codahale.com/about/contributors/

# LMAX Disruptor - The Disruptor pattern is used to help with speedy production and consumption of events.
Thank you! Michael Barker (https://github.com/mikeb01)

# MongoDB - Much of the persistence is provided by MongoDB.
Thank you MongoDB Team! (http://www.mongodb.org/)

# Heroku - Throughout development Heroku was always there for Peregrine when it needed a host.
Thank you Heroku Team! (https://www.heroku.com/)

## Why the name Peregrine?

The Peregrine Falcon is renowned for its speed, so is the Peregrine Trading Platform. The Paragrine Falcon can reach
speeds over 322 km/h (200 mph) during its characteristic hunting stoop (high speed dive), making it the fastest member
of the animal kingdom. (see: http://en.wikipedia.org/wiki/Peregrine_Falcon)

## How was the startup banner generated?

http://patorjk.com/software/taag/#p=display&f=Doom&t=Peregrine


