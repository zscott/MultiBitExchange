package org.multibit.exchange.domainmodel;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;
import javax.inject.Provider;

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
public abstract class AbstractSecurityOrderProvider implements Provider<SecurityOrder> {

  Random rand = new Random();

  AbstractSecurityOrderProvider() {
    rand.setSeed(System.currentTimeMillis());
  }

  protected ItemQuantity randomItemQuantity() {
    int randInt = Math.abs(rand.nextInt());
    BigDecimal bigDecimalAmount = new BigDecimal(randInt).movePointLeft(8).setScale(8, RoundingMode.FLOOR);
    return new ItemQuantity(bigDecimalAmount.toPlainString());
  }

  protected SecurityOrderId generateId() {
    return SecurityOrderId.next();

  }
}
