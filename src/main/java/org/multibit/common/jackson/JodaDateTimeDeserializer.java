package org.multibit.common.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.joda.time.DateTime;
import org.multibit.common.DateUtils;

import java.io.IOException;

public class JodaDateTimeDeserializer extends JsonDeserializer<DateTime> {

  @Override
  public DateTime deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
    String text = jp.getText().trim();
    return DateUtils.parseISO8601(text);
  }
}
