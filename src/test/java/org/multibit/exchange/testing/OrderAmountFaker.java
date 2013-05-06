package org.multibit.exchange.testing;

import org.multibit.exchange.domainmodel.ItemQuantity;

/**
 * <p>Faker to provide the following to tests:</p>
 * <ul>
 * <li>Fake instances of {@link org.multibit.exchange.domainmodel.ItemQuantity}</li>
 * </ul>
 *
 * @since 0.0.1
 *         
 */
public class OrderAmountFaker {

  public static ItemQuantity createValid() {
    return new ItemQuantity("10");
  }
}
