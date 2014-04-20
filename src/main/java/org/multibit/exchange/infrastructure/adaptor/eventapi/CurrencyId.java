package org.multibit.exchange.infrastructure.adaptor.eventapi;

import org.multibit.common.AbstractIdentifier;

/**
 * <p>Identifier to provide the following to the domain model:</p>
 * <ul>
 * <li>A unique identifier for {@link org.multibit.exchange.domain.model.Currency}</li>
 * </ul>
 *
 * @since 0.0.1
 */
public class CurrencyId extends AbstractIdentifier<String> {

  public CurrencyId(String identifier) {
    super(validate(identifier));
  }

  private static String validate(String identifier) {
    return identifier;
  }
}
