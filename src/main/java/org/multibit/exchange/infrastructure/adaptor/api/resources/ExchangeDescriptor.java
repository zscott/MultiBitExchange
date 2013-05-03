package org.multibit.exchange.infrastructure.adaptor.api.resources;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * <p>Descriptor to provide the following to {@link ExchangeResource}:</p>
 * <ul>
 * <li>ValueObject describing an exchange</li>
 * </ul>
 *
 * @since 0.0.1
 */
public class ExchangeDescriptor {

  private String identifier;

  public ExchangeDescriptor(
    @JsonProperty("identifier") String identifier) {
    this.identifier = identifier;
  }

  public String getIdentifier() {
    return identifier;
  }
}
