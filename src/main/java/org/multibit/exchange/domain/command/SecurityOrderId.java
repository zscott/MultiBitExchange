package org.multibit.exchange.domain.command;

import org.axonframework.domain.IdentifierFactory;

import java.io.Serializable;

/**
 * <p>Id for an {@link org.multibit.exchange.domain.model.SecurityOrder}</p>
 *
 * @since 0.0.1
 *  
 */
public class SecurityOrderId implements Serializable {

  private final String identifier;

  public SecurityOrderId() {
    this(IdentifierFactory.getInstance().generateIdentifier());
  }

  public SecurityOrderId(String identifier) {
    this.identifier = identifier;
  }

  public String getIdentifier() {
    return identifier;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    SecurityOrderId that = (SecurityOrderId) o;

    if (!identifier.equals(that.identifier)) return false;

    return true;
  }

  @Override
  public int hashCode() {
    return identifier.hashCode();
  }

  @Override
  public String toString() {
    return "SecurityOrderId{" +
        "identifier='" + identifier + '\'' +
        '}';
  }
}
