package org.multibit.exchange.infrastructure.adaptor.atmosphere;

import org.atmosphere.cpr.Broadcaster;
import org.atmosphere.cpr.BroadcasterFactory;
import org.multibit.exchange.domain.event.TradeExecutedEvent;

/**
 * <p>Utility to make it easier to work with Broadcasters.</p>
 *
 * @since 0.0.1
 */
public class BroadcastHelper {

  public static final BroadcasterFactory FACTORY = BroadcasterFactory.getDefault();

  public static final String TRADES_CHANNEL = "/trades";

  public static Broadcaster getTradeBroadcaster() {
    return FACTORY.lookup(TRADES_CHANNEL, true);
  }

  public static void broadcastTrade(TradeExecutedEvent event) {
    getTradeBroadcaster().broadcast(event);
  }

}
