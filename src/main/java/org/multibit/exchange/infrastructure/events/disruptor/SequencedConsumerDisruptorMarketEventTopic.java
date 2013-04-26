package org.multibit.exchange.infrastructure.events.disruptor;

import com.lmax.disruptor.dsl.Disruptor;
import org.multibit.exchange.domain.event.MarketEvent;
import org.multibit.exchange.domain.event.MarketEventTopic;

/**
 * <p>DisruptorTopic to provide the following to the application:</p>
 * <ul>
 * <li>An implementation that sequences consumers</li>
 * </ul>
 *
 * @since 0.0.1
 *         
 */
public class SequencedConsumerDisruptorMarketEventTopic extends DisruptorTopic<MarketEvent> implements MarketEventTopic {

  public SequencedConsumerDisruptorMarketEventTopic(Disruptor disruptor, DisruptorConsumer<MarketEvent>... consumers) {
    super(disruptor);
    registerConsumers(consumers);
  }

  public void registerConsumers(DisruptorConsumer<MarketEvent>... consumers) {
//    if (consumers.length >= 2) {
//      for (int i = 0; i < consumers.length - 1; i++) {
//        DisruptorConsumer<MarketEvent> consumer = consumers[i];
//        DisruptorConsumer<MarketEvent> nextConsumer = consumers[i + 1];
//        getDisruptor().after(consumer).handleEventsWith(nextConsumer);
//      }
//    } else {
//      getDisruptor().handleEventsWith(consumers[0]);
//    }
    getDisruptor().handleEventsWith(consumers);
  }

  @Override
  public void publish(MarketEvent event) {
    System.out.println(this.getClass().getName() + ": publish. Publishing to disruptor.");
    publishToDisruptor(event);
  }
}
