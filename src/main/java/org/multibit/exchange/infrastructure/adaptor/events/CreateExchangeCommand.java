package org.multibit.exchange.infrastructure.adaptor.events;

import org.multibit.exchange.domainmodel.ExchangeId;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * <p>Command used for creating a new {@link org.multibit.exchange.domainmodel.Exchange}</p>
 *
 * @since 0.0.1
 */
public class CreateExchangeCommand {

  private final ExchangeId exchangeId;

  public CreateExchangeCommand(ExchangeId exchangeId) {
    checkNotNull(exchangeId, "exchangeId must not be null");

    this.exchangeId = exchangeId;
  }

  public ExchangeId getExchangeId() {
    return exchangeId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    CreateExchangeCommand that = (CreateExchangeCommand) o;

    if (!exchangeId.equals(that.exchangeId)) return false;

    return true;
  }

  @Override
  public int hashCode() {
    return exchangeId.hashCode();
  }
}
