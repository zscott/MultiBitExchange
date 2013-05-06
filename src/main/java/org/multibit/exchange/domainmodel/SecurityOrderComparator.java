package org.multibit.exchange.domainmodel;

import java.util.Comparator;

public class SecurityOrderComparator implements Comparator<SecurityOrder> {

  @Override
  public int compare(SecurityOrder securityOrder1, SecurityOrder securityOrder2) {
    long millis2 = securityOrder2.getCreatedTime().getMillis();
    String rawId2 = securityOrder2.getId().getRawId();
    long millis1 = securityOrder1.getCreatedTime().getMillis();
    String rawId1 = securityOrder1.getId().getRawId();
    return (millis2>millis1)?-1:(millis2<millis1)?1:rawId1.compareTo(rawId2);
  }
}
