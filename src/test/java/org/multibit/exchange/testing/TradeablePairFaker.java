package org.multibit.exchange.testing;

import org.multibit.exchange.domainmodel.Currency;
import org.multibit.exchange.domainmodel.TradeableItem;
import org.multibit.exchange.domainmodel.TradeablePair;

/**
 * <p>Faker to provide the following to tests:</p>
 * <ul>
 * <li>Instances of a {@link TradeablePair}</li>
 * </ul>
 *
 * @since 0.0.1
 *         
 */
public class TradeablePairFaker {

  public static TradeablePair createValid() {
    TradeableItem item = TradeableItemFaker.createValid();
    Currency currency = CurrencyFaker.createValid();
    return new TradeablePair(item, currency);
  }
}
