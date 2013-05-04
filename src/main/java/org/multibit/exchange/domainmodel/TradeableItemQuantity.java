package org.multibit.exchange.domainmodel;

import com.google.common.base.Strings;
import java.math.BigDecimal;


import static com.google.common.base.Preconditions.checkArgument;

/**
 * <p>ValueObject to provide the following to the domain model:</p>
 * <ul>
 * <li>Representation of a quantity of some TradeableItem</li>
 * </ul>
 *
 * @since 0.0.1
 */
public class TradeableItemQuantity {

  private final BigDecimal quantity;

  public TradeableItemQuantity(String quantityStr) {
    checkArgument(!Strings.isNullOrEmpty(quantityStr), "must not be null or empty: '%s'", quantityStr);
    quantity = new BigDecimal(quantityStr);
    checkArgument(quantity.compareTo(BigDecimal.ZERO) > 0, "must be greater than 0: '%s'", quantity);

  }
}
