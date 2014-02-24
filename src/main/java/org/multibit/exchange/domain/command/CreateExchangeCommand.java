package org.multibit.exchange.domain.command;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;
import org.multibit.exchange.domain.model.ExchangeId;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * <p>Command used for creating a new {@link org.multibit.exchange.domain.model.Exchange}</p>
 *
 * @since 0.0.1
 */
public class CreateExchangeCommand {

  @TargetAggregateIdentifier
  private final ExchangeId exchangeId;

  public CreateExchangeCommand(ExchangeId exchangeId) {
    checkNotNull(exchangeId, "exchangeId must not be null");
    this.exchangeId = exchangeId;
  }

  public ExchangeId getExchangeId() {
    return exchangeId;
  }

  @Override
  public String toString() {
    return "CreateExchangeCommand{" +
        "exchangeId=" + exchangeId +
        '}';
  }
}
