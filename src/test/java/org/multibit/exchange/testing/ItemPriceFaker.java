package org.multibit.exchange.testing;

import org.multibit.exchange.domain.model.ItemPrice;

/**
 * <p>Faker to provide the following to tests:</p>
 * <ul>
 * <li>Fake instances of {@link org.multibit.exchange.domain.model.ItemPrice}</li>
 * </ul>
 *
 * @since 0.0.1
 *  
 */
public class ItemPriceFaker {

  public static ItemPrice createValid() {
    return new ItemPrice("9876.00000001");
  }
}
