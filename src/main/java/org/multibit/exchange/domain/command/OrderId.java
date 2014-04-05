package org.multibit.exchange.domain.command;

import org.axonframework.domain.IdentifierFactory;
import org.multibit.common.AbstractIdentifier;

import java.io.Serializable;

/**
 * <p>Id for an {@link org.multibit.exchange.domain.model.SecurityOrder}</p>
 *
 * @since 0.0.1
 *  
 */
public class OrderId extends AbstractIdentifier<String> implements Serializable {

  public OrderId() {
    super(IdentifierFactory.getInstance().generateIdentifier());
  }
}
