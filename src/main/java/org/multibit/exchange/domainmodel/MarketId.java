package org.multibit.exchange.domainmodel;

import com.google.common.base.Strings;

import java.io.Serializable;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * <p>Identifier to provide the following to the domain model:</p>
 * <ul>
 * <li>A unique identifier for {@link org.multibit.exchange.infrastructure.adaptor.events.MarketAggregateRoot}s</li>
 * </ul>
 *
 * @since 0.0.1
 *         
 */
public class MarketId implements Serializable {

  private final String rawId;

  private static final String THE_ONLY_MARKET_ID_STRING = "THE-ONLY-MARKET";

  public MarketId(String rawId) {
    checkArgument(!Strings.isNullOrEmpty(rawId), "rawId must not be null or empty");

    this.rawId = rawId;
  }

  public static MarketId get() {
    return new MarketId(THE_ONLY_MARKET_ID_STRING);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    MarketId marketId = (MarketId) o;

    if (!rawId.equals(marketId.rawId)) return false;

    return true;
  }

  @Override
  public int hashCode() {
    return rawId.hashCode();
  }

  @Override
  public String toString() {
    return "MarketId{" +
        "rawId='" + rawId + '\'' +
        '}';
  }
}
