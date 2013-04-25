package org.multibit.exchange.infrastructure.events.disruptor;

import com.lmax.disruptor.dsl.Disruptor;

import org.multibit.exchange.domain.market.MarketEvent;
import org.multibit.exchange.domain.market.MarketEventPublisher;

/**
 * <p>A concrete MarketEventPublisher that uses an LMAX Disruptor.</p>
 *
 * @since 0.0.1
 *         
 */
public class DisruptorMarketEventPublisher extends DisruptorPublisher<MarketEvent> implements MarketEventPublisher {

  public DisruptorMarketEventPublisher(Disruptor disruptor) {
    super(disruptor);
  }
}
