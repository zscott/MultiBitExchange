package org.multibit.exchange.domainmodel;

import java.io.Serializable;
import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Domain object that provides the following to the domain model:</p>
 * <ul>
 * <li>Support for trading one {@link org.multibit.exchange.domainmodel.TradeableItem} for
 * another {@link org.multibit.exchange.domainmodel.TradeableItem} through the
 * addition and removal of {@link Order}s</li>
 * <li>An aggregate containing an {@link OrderBook} which contains {@link Order}s</li>
 * </ul>
 * <p/>
 * <p>
 * The Security does not maintain any history. It matches Bids and Asks and emits events such as
 * {@link OrderAddedEvent}, {@link OrderRemovedEvent}, {@link OrderExecuted} which can be used
 * by {@link ReadModelBuilder}s to maintain any type of persistent read model.
 * </p>
 *
 * @since 0.0.1
 *         
 */
public class Security extends AbstractAnnotatedAggregateRoot implements Serializable {

  private static Logger LOGGER = LoggerFactory.getLogger(Security.class);

  @AggregateIdentifier
  private SecurityId id;

  /**
   * The name of this security.
   */
  private String tickerSymbol;

  /**
   * The pair of {@link org.multibit.exchange.domainmodel.TradeableItem}s that can be traded in this security.
   */
  private TradeablePair tradeablePair;

  public Security() {
  }

  @CommandHandler
  public Security(CreateSecurityCommand command) {
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

    tickerSymbol = event.getTickerSymbol();

    tradeablePair = new TradeablePair(
        new TradeableItem(event.getTradeableItemSymbol()),
        new TradeableItem(event.getCurrencySymbol()));
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Security that = (Security) o;

    if (!id.equals(that.id)) return false;

    return true;
  }

  @Override
  public int hashCode() {
    return id.hashCode();
  }

  @Override
  public String toString() {
    return "Security{" +
        "id=" + id +
        ", tickerSymbol='" + tickerSymbol + '\'' +
        ", tradeablePair=" + tradeablePair +
        '}';
  }
}
