package org.multibit.exchange.infrastructure.adaptor.eventapi;

import org.axonframework.domain.IdentifierFactory;
import org.multibit.common.AbstractIdentifier;

/**
 * <p>Identifier to provide the following to the domain model:</p>
 * <ul>
 * <li>A unique identifier for {@link org.multibit.exchange.domain.model.Exchange}</li>
 * </ul>
 *
 * @since 0.0.1
 */
public class ExchangeId extends AbstractIdentifier<String> {

  public ExchangeId() {
    super(IdentifierFactory.getInstance().generateIdentifier());
  }

  public ExchangeId(String identifier) {
    super(identifier);
  }
}
