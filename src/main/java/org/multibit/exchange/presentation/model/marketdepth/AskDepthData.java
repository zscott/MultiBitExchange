package org.multibit.exchange.presentation.model.marketdepth;

import org.multibit.exchange.domain.model.Side;

public class AskDepthData extends DepthData {
  public AskDepthData() {
    super(Side.SELL);
  }
}
