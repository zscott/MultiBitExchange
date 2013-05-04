package org.multibit.exchange.domainmodel;

import com.google.common.base.Strings;
import java.math.BigDecimal;


import static com.google.common.base.Preconditions.checkArgument;

/**
 * An order amount.
 *
 * @since 0.0.1
 *         
 */
public class OrderAmount {

  private static final BigDecimal ZERO = new BigDecimal("0");
  public static final int MAX_PRECISION = 8;
  private static final BigDecimal MAX_ORDER = new BigDecimal("10000000");
  private static final BigDecimal MIN_ORDER = new BigDecimal("0.001");

  private final BigDecimal amount;

  public OrderAmount(String orderAmount) {

    checkArgument(!Strings.isNullOrEmpty(orderAmount), "orderAmount must not be null or empty");
    this.amount = new BigDecimal(orderAmount);

    checkArgument(amount.compareTo(ZERO) > 0,
        "orderAmount must not be negative");
    int actualPrecision = getNumberOfDecimalPlaces(orderAmount);
    checkArgument(actualPrecision <= MAX_PRECISION,
        "orderAmount must not have more than " + MAX_PRECISION + " decimal places, was: '%d'", actualPrecision);
    checkArgument(amount.compareTo(MAX_ORDER) <= 0,
        "orderAmount must not be greater than " + MAX_ORDER);
    checkArgument(amount.compareTo(MIN_ORDER) >= 0,
        "orderAmount must be at least " + MIN_ORDER);
  }

  public String getRaw() {
    return amount.toString();
  }

  private static int getNumberOfDecimalPlaces(BigDecimal bigDecimal) {
    String string = bigDecimal.stripTrailingZeros().toPlainString();
    return getNumberOfDecimalPlaces(string);
  }

  private static int getNumberOfDecimalPlaces(String string) {
    int index = string.indexOf(".");
    return index < 0 ? 0 : string.length() - index - 1;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    OrderAmount that = (OrderAmount) o;

    if (!amount.equals(that.amount)) return false;

    return true;
  }

  @Override
  public int hashCode() {
    return amount.hashCode();
  }

  @Override
  public String toString() {
    return "OrderAmount{" +
        "amount=" + amount +
        '}';
  }
}
