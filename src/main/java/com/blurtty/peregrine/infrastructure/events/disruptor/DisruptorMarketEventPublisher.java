package com.blurtty.peregrine.infrastructure.events.disruptor;

import com.lmax.disruptor.dsl.Disruptor;

import com.blurtty.peregrine.domain.market.MarketEvent;
import com.blurtty.peregrine.domain.market.MarketEventPublisher;

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
