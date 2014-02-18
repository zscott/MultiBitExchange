package org.multibit.exchange.domain.model;

import com.google.common.base.Strings;

import java.io.Serializable;
import java.math.BigDecimal;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * <p>Price of an item.</p>
 *
 * @since 0.0.1
 */
public class ItemPrice implements Serializable {

  private static final BigDecimal ZERO = new BigDecimal("0");

  public static final int MAX_PRECISION = 8;
  private static final BigDecimal MAX_PRICE = new BigDecimal(1000000000000000l);
  private static final long MAX_DIGITS = String.valueOf(MAX_PRICE).length() + MAX_PRECISION + 1; // 1000000000000000.00000000

  private final BigDecimal itemPrice;

  public ItemPrice(String priceStr) {
    checkArgument(!Strings.isNullOrEmpty(priceStr), "price must not be null or empty");
    checkArgument(priceStr.length() <= MAX_DIGITS, "price must not exceed max digits: " + MAX_DIGITS + " (including decimal)");
    itemPrice = new BigDecimal(priceStr);

    checkArgument(itemPrice.compareTo(ZERO) > 0, "price must not be zero or negative");
    checkArgument(itemPrice.compareTo(MAX_PRICE) <= 0, "price must not exceed maximum: " + MAX_PRICE);
    int actualPrecision = getNumberOfDecimalPlaces(priceStr);
    checkArgument(actualPrecision <= MAX_PRECISION,
        "price must not have more than " + MAX_PRECISION + " decimal places, was: '%d'", actualPrecision);
  }

  public String getRaw() {
    return itemPrice.toString();
  }

  private static int getNumberOfDecimalPlaces(BigDecimal bigDecimal) {
    String string = bigDecimal.stripTrailingZeros().toPlainString();
    return getNumberOfDecimalPlaces(string);
  }

  private static int getNumberOfDecimalPlaces(String string) {
    int index = string.indexOf(".");
    return index < 0 ? 0 : string.length() - index - 1;
  }

  public BigDecimal toBigDecimal() {
    return itemPrice;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    ItemPrice itemPrice1 = (ItemPrice) o;

    if (!itemPrice.equals(itemPrice1.itemPrice)) return false;

    return true;
  }

  @Override
  public int hashCode() {
    return itemPrice.hashCode();
  }

  @Override
  public String toString() {
    return "itemPrice{" +
        "quantity=" + itemPrice +
        '}';
  }

}
