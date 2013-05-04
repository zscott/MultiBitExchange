package org.multibit.exchange.domainmodel;

import java.util.Comparator;

public class PriceComparator implements Comparator<SecurityOrder> {

  @Override
  public int compare(SecurityOrder securityOrder, SecurityOrder securityOrder2) {
    return securityOrder2.getPriceInt() - securityOrder.getPriceInt();
  }
}
