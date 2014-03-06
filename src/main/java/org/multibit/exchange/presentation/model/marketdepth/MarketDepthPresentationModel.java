package org.multibit.exchange.presentation.model.marketdepth;

import org.multibit.exchange.presentation.model.common.PresentationModelMetaData;

public class MarketDepthPresentationModel {

  private PresentationModelMetaData metaData;

  private BidDepthData bidDepthData = new BidDepthData();

  private AskDepthData askDepthData = new AskDepthData();

}
