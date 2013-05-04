package org.multibit.exchange.domainmodel;

public abstract class SecurityOrder {

  public abstract OrderType getType();

  public abstract int getPriceInt();
}
