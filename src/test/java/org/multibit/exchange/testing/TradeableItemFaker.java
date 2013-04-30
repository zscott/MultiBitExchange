package org.multibit.exchange.testing;

import org.multibit.exchange.domainmodel.TradeableItem;

/**
 * <p>Faker to provide the following to tests:</p>
 * <ul>
 * <li>Fake instances of {@link TradeableItem}</li>
 * </ul>
 * <p>Example:</p>
 * <pre>
 * </pre>
 *
 * @since 0.0.1
 */
public class TradeableItemFaker {

  public static TradeableItem createValid() {
    return new TradeableItem("TEST_ITEM");
  }

}
