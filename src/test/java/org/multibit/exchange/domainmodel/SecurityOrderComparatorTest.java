package org.multibit.exchange.domainmodel;

import com.google.common.collect.Sets;
import org.joda.time.DateTime;
import org.junit.Test;
import org.multibit.common.DateUtils;
import org.multibit.exchange.testing.CurrencyPairFaker;

import java.util.TreeSet;

import static org.fest.assertions.api.Assertions.assertThat;

public class SecurityOrderComparatorTest {

  @Test
  public void testGetFirstOrder() {
    // Arrange
    SecurityOrderComparator comparator = new SecurityOrderComparator();
    TreeSet<SecurityOrder> orderedSet = Sets.newTreeSet(comparator);
    ItemQuantity quantity = new ItemQuantity("10");

    DateTime createdTime1 = DateUtils.thenUtc(2000, 1, 2, 1, 0, 0);
    SellOrder order1 = new SellOrder(SecurityOrderId.next(), OrderType.marketOrder(), CurrencyPairFaker.createValid(), quantity, createdTime1);

    DateTime oneSecondAfterCreatedTime1 = DateUtils.thenUtc(2000, 1, 2, 1, 0, 1);
    SellOrder order2 = new SellOrder(SecurityOrderId.next(), OrderType.marketOrder(), CurrencyPairFaker.createValid(), quantity, oneSecondAfterCreatedTime1);

    orderedSet.add(order1);
    orderedSet.add(order2);

    // Act
    SecurityOrder firstOrder = orderedSet.first();

    // Assert
    assertThat(firstOrder).isEqualTo(order1);
  }
}
