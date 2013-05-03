package org.multibit.exchange.infrastructure.adaptor.events;

import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;
import org.multibit.exchange.domainmodel.Market;
import org.multibit.exchange.domainmodel.MarketId;
import org.multibit.exchange.domainmodel.Ticker;
import org.multibit.exchange.domainmodel.TradeableItem;
import org.multibit.exchange.domainmodel.TradeablePair;

/**
 * <p>AggregateRoot to provide the following to the Axon Framework:</p>
 * <ul>
 * <li>An axon-specific representation of the AggregateRoot: {@link Market} in the domain model.</li>
 * <li>Event handling methods for events targeted at this aggregate root.</li>
 * <li>Command handling methods for events targeted at this aggregate root.</li>
 * </ul>
 *
 * @since 0.0.1
 */
public class MarketAggregateRoot extends AbstractAnnotatedAggregateRoot {

  @AggregateIdentifier
  private MarketId id = MarketId.get();

  private Market market = new Market();

  // TODO - ZS - rename Market to Exchange
  public MarketAggregateRoot() {
  }




  /*
   * Command Handlers
   */
  @CommandHandler
  void createSecurity(CreateSecurityCommand command) {
    apply(new SecurityCreatedEvent(
      command.getMarketId(),
      command.getTicker(),
      command.getTradeableItem(),
      command.getCurrency()));
  }

  @CommandHandler
  public void placeMarketBidOrder(PlaceMarketBidOrderCommand command) {
    apply(new MarketBidOrderPlacedEvent(command.getMarketId(), command.getQuantity()));
  }




  /*
   * Event Handlers
   */
  @EventHandler
  public void on(SecurityCreatedEvent event) {
    Ticker ticker = new Ticker(event.getTickerSymbol());
    TradeablePair tradeablePair = new TradeablePair(
      new TradeableItem(event.getTradeableItemSymbol()),
      new TradeableItem(event.getCurrencySymbol()));

    market.addSecurity(ticker, tradeablePair);
  }




  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    MarketAggregateRoot that = (MarketAggregateRoot) o;

    if (!id.equals(that.id)) return false;

    return true;
  }

  @Override
  public int hashCode() {
    return id.hashCode();
  }
}
