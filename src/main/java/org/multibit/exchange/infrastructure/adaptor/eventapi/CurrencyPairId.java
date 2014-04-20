package org.multibit.exchange.infrastructure.adaptor.eventapi;

import org.multibit.common.AbstractIdentifier;

/**
 * <p>Identifier to provide the following to the domain model:</p>
 * <ul>
 * <li>A unique identifier for {@link org.multibit.exchange.domain.model.CurrencyPair}</li>
 * </ul>
 *
 * @since 0.0.1
 */
public class CurrencyPairId extends AbstractIdentifier<String> {

  public CurrencyPairId(String identifier) {
    super(upperCase(identifier));
  }

  private static String upperCase(String identifier) {
    if (identifier == null)
      return null;
    return identifier.toUpperCase();
  }
}
