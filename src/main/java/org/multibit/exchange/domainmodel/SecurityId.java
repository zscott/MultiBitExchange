package org.multibit.exchange.domainmodel;

import java.io.Serializable;
import java.util.UUID;

/**
 * <p>Identifier to provide the following to the domain model:</p>
 * <ul>
 * <li>A unique identifier for {@link Security}s</li>
 * </ul>
 *
 * @since 0.0.1
 *         
 */
public class SecurityId implements Serializable {

  private final String rawId;

  private SecurityId(String rawId) {
    this.rawId = rawId;
  }

  public static SecurityId next() {
    return new SecurityId(nextRawId());
  }

  private static String nextRawId() {
    return UUID.randomUUID().toString();
  }

  @Override
  public String toString() {
    return "SecurityId{" +
        "rawId='" + rawId + '\'' +
        '}';
  }
}
