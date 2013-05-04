package org.multibit.exchange.testing;

import org.multibit.exchange.domainmodel.OrderAmount;

/**
 * <p>Faker to provide the following to tests:</p>
 * <ul>
 * <li>Fake instances of {@link OrderAmount}</li>
 * </ul>
 *
 * @since 0.0.1
 *         
 */
public class OrderAmountFaker {

  public static OrderAmount createValid() {
    return new OrderAmount("10");
  }
}
