package com.blurtty.peregrine.infrastructure.events.disruptor;

import com.blurtty.peregrine.domain.MarketEvent;
import com.blurtty.peregrine.domain.MarketEventPublisherService;
import com.lmax.disruptor.dsl.Disruptor;

/**
 * <p>A concrete MarketEventPublisherService that uses an LMAX Disruptor.</p>
 *
 * @since 0.0.1
 *         
 */
public class DisruptorMarketEventPublisherService extends DisruptorPublisher<MarketEvent> implements MarketEventPublisherService {

  public DisruptorMarketEventPublisherService(Disruptor disruptor) {
    super(disruptor);
  }
}