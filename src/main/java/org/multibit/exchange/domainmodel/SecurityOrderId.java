package org.multibit.exchange.domainmodel;

import java.util.UUID;

/**
 * <p>Id for an {@link SecurityOrder}</p>
 *
 * @since 0.0.1
 *         
 */
public class SecurityOrderId {

  private final String rawId;

  public SecurityOrderId(String rawId) {

    this.rawId = rawId;
  }

  public static SecurityOrderId next() {
    return new SecurityOrderId(UUID.randomUUID().toString());
  }
}
