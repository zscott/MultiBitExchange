package org.multibit.common;

import org.multibit.exchange.infrastructure.adaptor.eventapi.ExchangeId;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * <p>Abstract class to provide a reusable implementation of a string based identifier.</p>
 *
 * @since 0.0.1
 */
public abstract class AbstractIdentifier<T> {
  protected final T identifier;

  public AbstractIdentifier(T identifier) {
    validateIdentifier(identifier);
    this.identifier = identifier;
  }

  private void validateIdentifier(T identifier) {
    String message = "identifier must not be null or empty";
    checkArgument(identifier != null, message);
    checkArgument(!String.valueOf(identifier).isEmpty(), message);
  }

  public T getIdentifier() {
    return identifier;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    ExchangeId exchangeId = (ExchangeId) o;

    if (!this.identifier.equals(exchangeId.identifier)) return false;

    return true;
  }

  @Override
  public int hashCode() {
    return identifier.hashCode();
  }

  @Override
  public String toString() {
    return String.valueOf(identifier);
  }
}
