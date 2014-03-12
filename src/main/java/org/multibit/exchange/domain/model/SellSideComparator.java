package org.multibit.exchange.domain.model;

import java.io.Serializable;
import java.util.Comparator;

class SellSideComparator implements Comparator<PricedItem>, Serializable {

  @Override
  public int compare(PricedItem o1, PricedItem o2) {
    return o1.getBigDecimalPrice().compareTo(o2.getBigDecimalPrice());
  }
}
