package org.multibit.exchange.domain.command;

import java.io.Serializable;
import java.util.UUID;

/**
 * <p>Id for an {@link org.multibit.exchange.domain.model.SecurityOrder}</p>
 *
 * @since 0.0.1
 *  
 */
public class SecurityOrderId implements Serializable {

  private final String rawId;

  public SecurityOrderId(String rawId) {
    this.rawId = rawId;
  }

  public static SecurityOrderId next() {
    return new SecurityOrderId(UUID.randomUUID().toString());
  }

  public String getRawId() {
    return rawId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    SecurityOrderId that = (SecurityOrderId) o;

    if (!rawId.equals(that.rawId)) return false;

    return true;
  }

  @Override
  public int hashCode() {
    return rawId.hashCode();
  }

  @Override
  public String toString() {
    return "SecurityOrderId{" +
        "rawId='" + rawId + '\'' +
        '}';
  }
}
