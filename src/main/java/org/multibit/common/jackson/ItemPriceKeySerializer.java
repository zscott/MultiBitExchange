package org.multibit.common.jackson;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.multibit.exchange.domain.model.ItemPrice;

import java.io.IOException;

public class ItemPriceKeySerializer extends StdSerializer<ItemPrice> {

  public ItemPriceKeySerializer() {
    super(ItemPrice.class);
  }

  @Override
  public void serialize(ItemPrice value, JsonGenerator jgen, SerializerProvider provider)
          throws IOException, JsonGenerationException {
    ObjectMapper mapper = new ObjectMapper();
    jgen.writeFieldName(mapper.writeValueAsString(value.getRaw()));
  }
}
