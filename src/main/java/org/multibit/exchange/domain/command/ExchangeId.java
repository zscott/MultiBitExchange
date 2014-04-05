package org.multibit.exchange.domain.command;

import com.google.common.base.Strings;

import java.io.Serializable;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * <p>Identifier to provide the following to the domain model:</p>
 * <ul>
 * <li>A unique identifier for {@link org.multibit.exchange.domain.model.Exchange}s</li>
 * </ul>
 *
 * @since 0.0.1
 */
public class ExchangeId implements Serializable {

  private final String exchangeId;

  public ExchangeId(String exchangeId) {
    checkArgument(!Strings.isNullOrEmpty(exchangeId), "exchangeId must not be null or empty");

    this.exchangeId = exchangeId;
  }

  public String getCode() {
    return exchangeId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    ExchangeId exchangeId = (ExchangeId) o;

    if (!this.exchangeId.equals(exchangeId.exchangeId)) return false;

    return true;
  }

  @Override
  public int hashCode() {
    return exchangeId.hashCode();
  }

  @Override
  public String toString() {
    return exchangeId;
  }
}
