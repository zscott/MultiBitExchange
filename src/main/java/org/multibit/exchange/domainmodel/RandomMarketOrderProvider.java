package org.multibit.exchange.domainmodel;

import static org.multibit.common.DateUtils.nowUtc;

/**
 * <p>[Pattern] to provide the following to {@link Object}:</p>
 * <ul>
 * <li></li>
 * </ul>
 * <p>Example:</p>
 * <pre>
 * </pre>
 *
 * @since 0.0.1
 *         
 */
public class RandomMarketOrderProvider extends AbstractSecurityOrderProvider {
  @Override
  public SecurityOrder get() {
    if (rand.nextBoolean())
      return new MarketBidOrder(generateId(), randomItemQuantity(), nowUtc());
    else
      return new MarketAskOrder(generateId(), randomItemQuantity(), nowUtc());
  }
}
