package org.multibit.exchange.presentation.model.marketdepth;

import org.multibit.exchange.domain.model.Side;
import org.multibit.exchange.presentation.model.common.PresentationModelMetaData;

public class MarketDepthPresentationModel {

  private PresentationModelMetaData metaData;

  private DepthData bidDepthData = new DepthData(Side.BUY);

  private DepthData askDepthData = new DepthData(Side.SELL);

}
