package org.multibit.exchange.infrastructure.adaptor.api.readmodel;

import org.multibit.exchange.domainmodel.SecurityCreatedEvent;

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

  void handleSecurityCreated(SecurityCreatedEvent event);

}
