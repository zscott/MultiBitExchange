package org.multibit.exchange.domain.model;

import java.io.Serializable;
import java.util.Comparator;

class BuySideComparator implements Comparator<PricedItem>, Serializable {

  @Override
  public int compare(PricedItem o1, PricedItem o2) {
    return o2.getBigDecimalPrice().compareTo(o1.getBigDecimalPrice());
  }
}
