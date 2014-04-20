package org.multibit.exchange.infrastructure.adaptor.eventapi;

import org.axonframework.domain.IdentifierFactory;
import org.multibit.common.AbstractIdentifier;

/**
 * <p>Id for an {@link org.multibit.exchange.domain.model.SecurityOrder}</p>
 *
 * @since 0.0.1
 *  
 */
public class OrderId extends AbstractIdentifier<String> {

  public OrderId() {
    super(IdentifierFactory.getInstance().generateIdentifier());
  }
}
