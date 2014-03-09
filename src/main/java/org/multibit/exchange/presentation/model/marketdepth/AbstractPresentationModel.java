package org.multibit.exchange.presentation.model.marketdepth;

import org.joda.time.DateTime;
import org.multibit.common.DateUtils;
import org.multibit.common.Entity;
import org.multibit.exchange.presentation.model.common.PresentationModelMetaData;

public abstract class AbstractPresentationModel implements Entity<String> {

  protected PresentationModelMetaData metaData;

  protected AbstractPresentationModel() {
    metaData = new PresentationModelMetaData();
    metaData.setLastUpdatedTimestamp(DateUtils.nowUtc());
  }

  public PresentationModelMetaData getMetaData() {
    return metaData;
  }

  public void setMetaData(PresentationModelMetaData metaData) {
    this.metaData = metaData;
  }

  public void setLastUpdatedTimestamp(DateTime updateTimestamp) {
    this.metaData.setLastUpdatedTimestamp(updateTimestamp);
  }

  public void touchLastUpdatedTimestamp() {
    setLastUpdatedTimestamp(DateUtils.nowUtc());
  }
}
