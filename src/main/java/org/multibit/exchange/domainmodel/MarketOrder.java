package org.multibit.exchange.domainmodel;

/**
 * <p>[Pattern] to provide the following to {@link [Object]}:</p>
 * <ul>
 * <li></li>
 * </ul>
 * <p>Example:</p>
 * <pre>
 * </pre>
 *
 * @since 0.0.1
 */
public class MarketOrder extends OrderType {

  @Override
  public boolean isMarket() {
    return true;
  }
}
