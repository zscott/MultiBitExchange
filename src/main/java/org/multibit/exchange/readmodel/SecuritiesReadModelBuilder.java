package org.multibit.exchange.readmodel;

import org.multibit.exchange.domain.SecurityCreatedEvent;

/**
 * <p>ReadModelBuilder to provide the following to the application:</p>
 * <ul>
 * <li>The single writer of the market read model</li>
 * <li>On-the-fly maintenance of the market read model</li>
 * </ul>
 *
 * @since 0.0.1
 *         
 */
public interface SecuritiesReadModelBuilder {

  void handleSecurityCreated(SecurityCreatedEvent event);

}
