package org.multibit.common.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.joda.time.DateTime;
import org.multibit.common.DateUtils;

import java.io.IOException;

public class JodaDateTimeSerializer extends JsonSerializer<DateTime> {

  @Override
  public void serialize(DateTime value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
    String text = DateUtils.formatISO8601(value);
    jgen.writeString(text);
  }
}
