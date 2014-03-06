package org.multibit.common.jackson;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdKeyDeserializer;
import org.multibit.exchange.domain.model.ItemPrice;

public class ItemPriceKeyDeserializer extends StdKeyDeserializer {

  protected ItemPriceKeyDeserializer() {
    super(ItemPrice.class);
  }

  @Override
  protected Object _parse(String key, DeserializationContext ctxt) throws JsonMappingException {
    ObjectMapper mapper = new ObjectMapper();
    try {
      return mapper.readValue(key, ItemPrice.class);
    } catch (Exception e) {
      throw ctxt.weirdKeyException(_keyClass, key, e.getMessage());
    }
  }
}
