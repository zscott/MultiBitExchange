package org.multibit.exchange.presentation.model.marketdepth;

import org.multibit.exchange.domain.model.Side;

public class BidDepthData extends DepthData {
  public BidDepthData() {
    super(Side.BUY);
  }
}
