package org.multibit.exchange.presentation.model.common;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.joda.time.DateTime;
import org.multibit.common.jackson.JodaDateTimeDeserializer;
import org.multibit.common.jackson.JodaDateTimeSerializer;

public class PresentationModelMetaData {

  @JsonSerialize(using = JodaDateTimeSerializer.class)
  @JsonDeserialize(using = JodaDateTimeDeserializer.class)
  private DateTime lastUpdatedTimestamp;

  public void setLastUpdatedTimestamp(DateTime lastUpdatedTimestamp) {
    this.lastUpdatedTimestamp = lastUpdatedTimestamp;
  }

  public DateTime getLastUpdatedTimestamp() {
    return lastUpdatedTimestamp;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    PresentationModelMetaData that = (PresentationModelMetaData) o;

    if (lastUpdatedTimestamp != null ? !lastUpdatedTimestamp.equals(that.lastUpdatedTimestamp) : that.lastUpdatedTimestamp != null)
      return false;

    return true;
  }

  @Override
  public int hashCode() {
    return lastUpdatedTimestamp != null ? lastUpdatedTimestamp.hashCode() : 0;
  }
}
