package org.multibit.exchange.infrastructure.adaptor.api.resources.atmosphere;

import org.atmosphere.cpr.BroadcasterFactory;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.annotation.AnnotationEventListenerAdapter;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.multibit.exchange.domain.model.Trade;
import org.multibit.exchange.domain.event.TradeExecutedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

/**
 * <p>Atmosphere/WebSocket stream that broadcasts trades.</p>
 *
 * @since 0.0.1
 */
public class TradeStream {

  private static Logger LOGGER = LoggerFactory.getLogger(TradeStream.class);

  @Inject
  public TradeStream(EventBus eventBus) {
    AnnotationEventListenerAdapter.subscribe(this, eventBus);
  }

  @EventHandler
  public void handle(TradeExecutedEvent event) {
    LOGGER.debug("all broadcasters: \n" + BroadcasterFactory.getDefault().lookupAll());
    LOGGER.debug("handling TradeExecutedEvent: {}", event);

    Trade trade = event.getTrade();
    BroadcasterFactory.getDefault().lookup("/*").broadcast(trade);
  }
}
