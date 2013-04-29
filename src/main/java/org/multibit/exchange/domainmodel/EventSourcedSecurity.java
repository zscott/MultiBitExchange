package org.multibit.exchange.domainmodel;

import java.io.Serializable;
import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;
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
public class EventSourcedSecurity extends AbstractAnnotatedAggregateRoot implements Serializable {

  private static Logger LOGGER = LoggerFactory.getLogger(EventSourcedSecurity.class);

  @AggregateIdentifier
  private SecurityId id;

  private Security security;

  public EventSourcedSecurity() {
  }

  @CommandHandler
  public EventSourcedSecurity(CreateSecurityCommand command) {
    LOGGER.debug("handling {}", command);
    apply(new SecurityCreatedEvent(
        command.getId(),
        command.getTickerSymbol(),
        command.getTradeableItemSymbol(),
        command.getCurrencySymbol()));
  }

  @EventHandler
  public void on(SecurityCreatedEvent event) {
    LOGGER.debug("handling {}", event);
    id = event.getId();

    TradeablePair tradeablePair = new TradeablePair(
        new TradeableItem(event.getTradeableItemSymbol()),
        new TradeableItem(event.getCurrencySymbol()));

    security = new Security(event.getTickerSymbol(), tradeablePair);
  }
}
