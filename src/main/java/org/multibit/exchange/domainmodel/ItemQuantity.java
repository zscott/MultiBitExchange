package org.multibit.exchange.domainmodel;

import com.google.common.base.Strings;
import java.io.Serializable;
import java.math.BigDecimal;


import static com.google.common.base.Preconditions.checkArgument;

/**
 * An item quantity.
 *
 * @since 0.0.1
 *         
 */
public class ItemQuantity implements Comparable<ItemQuantity>, Serializable {

  private static final BigDecimal ZERO = new BigDecimal("0");
  public static final int MAX_PRECISION = 8;
  private static final BigDecimal MAX_QUANTITY = new BigDecimal("10000000");
  private static final BigDecimal MIN_QUANTITY = new BigDecimal("0");

  private final BigDecimal quantity;

  public ItemQuantity(String itemQuantity) {

    checkArgument(!Strings.isNullOrEmpty(itemQuantity), "itemQuantity must not be null or empty");
    this.quantity = new BigDecimal(itemQuantity);

    checkArgument(quantity.compareTo(ZERO) >= 0,
        "itemQuantity must not be negative, was " + quantity);
    int numberOfDecimalPlaces = getNumberOfDecimalPlaces(quantity);
    checkArgument(numberOfDecimalPlaces <= MAX_PRECISION,
        "itemQuantity must not have more than " + MAX_PRECISION + " decimal places, was " + quantity + "(" + numberOfDecimalPlaces + ")");
    checkArgument(quantity.compareTo(MAX_QUANTITY) <= 0,
        "itemQuantity must not be greater than " + MAX_QUANTITY);
    checkArgument(quantity.compareTo(MIN_QUANTITY) >= 0,
        "itemQuantity must be at least " + MIN_QUANTITY);
  }

  public String getRaw() {
    return quantity.toString();
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

    ItemQuantity that = (ItemQuantity) o;

    if (!quantity.equals(that.quantity)) return false;

    return true;
  }

  @Override
  public int hashCode() {
    return quantity.hashCode();
  }

  @Override
  public String toString() {
    return "itemQuantity{" +
        "quantity=" + quantity +
        '}';
  }

  @Override
  public int compareTo(ItemQuantity o) {
    return quantity.compareTo(o.quantity);
  }

  public ItemQuantity plus(ItemQuantity that) {
    // fixme - This immutability looks like it will be inefficient
    return new ItemQuantity(this.quantity.add(that.quantity).toString());
  }

  public ItemQuantity minus(ItemQuantity that) {
    // fixme - This immutability looks like it will be inefficient
    return new ItemQuantity(this.quantity.subtract(that.quantity).toString());
  }

  public ItemQuantity min(ItemQuantity that) {
    // fixme - This immutability looks like it will be inefficient
    return new ItemQuantity(this.quantity.min(that.quantity).toString());
  }

  public ItemQuantity max(ItemQuantity that) {
    // fixme - This immutability looks like it will be inefficient
    return new ItemQuantity(this.quantity.max(that.quantity).toString());
  }

  public boolean isZero() {
    return quantity.equals(BigDecimal.ZERO);
  }

  public ItemQuantity add(ItemQuantity that) {
    return new ItemQuantity(this.quantity.add(that.quantity).toString());
  }
}
