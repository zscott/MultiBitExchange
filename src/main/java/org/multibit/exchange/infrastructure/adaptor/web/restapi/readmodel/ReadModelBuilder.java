package org.multibit.exchange.infrastructure.adaptor.web.restapi.readmodel;

import org.multibit.exchange.domain.event.CurrencyPairRegisteredEvent;

/**
 * <p>ReadModelBuilder to provide the following to the api adaptor:</p>
 * <ul>
 * <li>On-the-fly event based maintenance of read models</li>
 * </ul>
 *
 * @since 0.0.1
 *  
 */
public interface ReadModelBuilder {

  void handle(CurrencyPairRegisteredEvent event);

}
