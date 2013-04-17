package com.blurtty.peregrine.infrastructure.events.disruptor;

import com.blurtty.peregrine.domain.MarketEvent;
import com.blurtty.peregrine.domain.MarketEventPublisherService;
import com.lmax.disruptor.SleepingWaitStrategy;
import com.lmax.disruptor.WaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import javax.inject.Provider;
import java.util.concurrent.Executors;

/**
 * <p>InjectionProvider to provide the following to the application infrastructure:</p>
 * <ul>
 * <li>Instances of {@link: DisruptorMarketEventPublisherService} that can be used for dependency injection.</li>
 * </ul>
 *
 * @since 0.0.1
 *         
 */
public class DisruptorMarketEventPublisherInjectionProvider implements Provider<MarketEventPublisherService> {

  public static final int RING_SIZE = 1024;

  public static final WaitStrategy WAIT_STRATEGY = new SleepingWaitStrategy();

  public static final ProducerType PRODUCER_TYPE = ProducerType.MULTI;

  public static final int THREAD_COUNT = 1;

  @Override
  public MarketEventPublisherService get() {
    final Disruptor<DisruptorEventWrapper<MarketEvent>> disruptor = new Disruptor<DisruptorEventWrapper<MarketEvent>>(
        new DisruptorEventFactory<MarketEvent>(),
        RING_SIZE,
        Executors.newFixedThreadPool(THREAD_COUNT),
        PRODUCER_TYPE,
        WAIT_STRATEGY);

    disruptor.start();

    return new DisruptorMarketEventPublisherService(disruptor);
  }
}
