package org.multibit.exchange.testing;

import org.multibit.exchange.domainmodel.Currency;
import org.multibit.exchange.domainmodel.TradeableItem;
import org.multibit.exchange.domainmodel.CurrencyPair;

/**
 * <p>Faker to provide the following to tests:</p>
 * <ul>
 * <li>Instances of a {@link org.multibit.exchange.domainmodel.CurrencyPair}</li>
 * </ul>
 *
 * @since 0.0.1
 *         
 */
public class TradeablePairFaker {

  public static CurrencyPair createValid() {
    TradeableItem item = TradeableItemFaker.createValid();
    Currency currency = CurrencyFaker.createValid();
    return new CurrencyPair(item, currency);
  }
}
