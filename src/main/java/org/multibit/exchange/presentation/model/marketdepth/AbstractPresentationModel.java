package org.multibit.exchange.presentation.model.marketdepth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.joda.time.DateTime;
import org.multibit.common.DateUtils;
import org.multibit.common.Entity;
import org.multibit.exchange.presentation.model.common.PresentationModelMetaData;

public abstract class AbstractPresentationModel implements Entity<String> {

  @JsonProperty("_id")
  protected String _id;

  protected PresentationModelMetaData metaData;

  protected AbstractPresentationModel() {
    metaData = new PresentationModelMetaData();
    metaData.setLastUpdatedTimestamp(DateUtils.nowUtc());
  }

  @Override
  @JsonIgnore
  public String getId() {
    return _id;
  }

  @Override
  public void setId(String id) {
    _id = id;
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
