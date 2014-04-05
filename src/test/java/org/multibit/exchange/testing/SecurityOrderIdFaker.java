package org.multibit.exchange.testing;

import org.multibit.exchange.domain.command.SecurityOrderId;

import java.util.UUID;

/**
 * <p>Faker to provide the following to tests:</p>
 * <ul>
 * <li>Fake Order Ids</li>
 * </ul>
 *
 * @since 0.0.1
 */
public class SecurityOrderIdFaker {

  public static SecurityOrderId nextId() {
    return new SecurityOrderId(UUID.randomUUID().toString());
  }

}
