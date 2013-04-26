package org.multibit.exchange.infrastructure.guice;

import com.lmax.disruptor.SleepingWaitStrategy;
import com.lmax.disruptor.WaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import java.util.concurrent.Executors;
import javax.inject.Inject;
import javax.inject.Provider;
import org.multibit.exchange.domain.event.MarketEvent;
import org.multibit.exchange.domain.event.MarketEventTopic;
import org.multibit.exchange.infrastructure.adaptor.tradingengine.TradingEngineMarketEventSubscriber;
import org.multibit.exchange.infrastructure.events.disruptor.DisruptorConsumer;
import org.multibit.exchange.infrastructure.events.disruptor.DisruptorEventFactory;
import org.multibit.exchange.infrastructure.events.disruptor.DisruptorEventWrapper;
import org.multibit.exchange.infrastructure.events.disruptor.SequencedConsumerDisruptorMarketEventTopic;
import org.multibit.exchange.readmodel.MarketReadModelBuilder;

/**
 * <p>Provider to provide the following to guice:</p>
 * <ul>
 * <li>Provision of instances of MarketEventDisruptorTopic</li>
 * </ul>
 *
 * @since 0.0.1
 *         
 */
public class DisruptorMarketEventTopicProvider implements Provider<MarketEventTopic> {

  public static final int RING_SIZE = (int) Math.pow(2, 8);

  public static final WaitStrategy WAIT_STRATEGY = new SleepingWaitStrategy();

  public static final ProducerType PRODUCER_TYPE = ProducerType.MULTI;

  public static final int THREAD_COUNT = 1;

  private final TradingEngineMarketEventSubscriber businessLogicProcessor;
  private final MarketReadModelBuilder readModelBuilder;

  @Inject
  public DisruptorMarketEventTopicProvider(
      TradingEngineMarketEventSubscriber businessLogicProcessor,
      MarketReadModelBuilder readModelBuilder) {
    this.businessLogicProcessor = businessLogicProcessor;
    this.readModelBuilder = readModelBuilder;
  }

  @Override
  public MarketEventTopic get() {
    final Disruptor<DisruptorEventWrapper<MarketEvent>> disruptor = new Disruptor<DisruptorEventWrapper<MarketEvent>>(
        new DisruptorEventFactory<MarketEvent>(),
        RING_SIZE,
        Executors.newCachedThreadPool(),//(THREAD_COUNT),
        PRODUCER_TYPE,
        WAIT_STRATEGY);

    SequencedConsumerDisruptorMarketEventTopic topic = new SequencedConsumerDisruptorMarketEventTopic(
        disruptor,
        new DisruptorConsumer(businessLogicProcessor),
        new DisruptorConsumer(readModelBuilder));

    disruptor.start();

    return topic;
  }
}
