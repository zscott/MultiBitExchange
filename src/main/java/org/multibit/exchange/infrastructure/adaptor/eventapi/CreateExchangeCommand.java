package org.multibit.exchange.infrastructure.adaptor.eventapi;

/**
 * <p>Command used for creating a new {@link org.multibit.exchange.domain.model.Exchange}</p>
 *
 * @since 0.0.1
 */
public class CreateExchangeCommand extends ExchangeCommand {

  public CreateExchangeCommand(ExchangeId exchangeId) {
    super(exchangeId);
  }

  @Override
  public String toString() {
    return "CreateExchangeCommand{" +
        "exchangeId=" + exchangeId +
        '}';
  }
}
