package org.multibit.exchange.infrastructure.adaptor.axon;

import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;
import org.multibit.exchange.domain.CreateSecurityCommand;
import org.multibit.exchange.domain.Security;
import org.multibit.exchange.domain.SecurityCreatedEvent;
import org.multibit.exchange.domain.TradeableItem;
import org.multibit.exchange.domain.TradeablePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>EventSourcedAggregate to provide the following to the infrastructure layer:</p>
 * <ul>
 * <li>An EventSourcedAggregateRoot for a {@link Security}</li>
 * </ul>
 *
 * @since 0.0.1
 *         
 */
public class EventSourcedSecurity extends AbstractAnnotatedAggregateRoot {

  private static Logger LOGGER = LoggerFactory.getLogger(EventSourcedSecurity.class);

  @AggregateIdentifier
  private String tickerSymbol;

  private Security security;

  public EventSourcedSecurity() {
  }

  @CommandHandler
  public EventSourcedSecurity(CreateSecurityCommand command) {
    LOGGER.debug("handling {}", command);
    apply(new SecurityCreatedEvent(
        command.getTickerSymbol(),
        command.getTradeableItemSymbol(),
        command.getCurrencySymbol()));
  }

  @EventHandler
  public void on(SecurityCreatedEvent event) {
    LOGGER.debug("handling {}", event);
    tickerSymbol = event.getTickerSymbol();

    TradeablePair tradeablePair = new TradeablePair(
        new TradeableItem(event.getTradeableItemSymbol()),
        new TradeableItem(event.getCurrencySymbol()));

    security = new Security(tickerSymbol, tradeablePair);
  }

}
