package org.multibit.exchange.infrastructure.adaptor.persistence.mongo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import org.mongojack.JacksonDBCollection;
import org.multibit.exchange.infrastructure.adaptor.web.restapi.readmodel.ReadServiceInitializationException;

/**
 * <p>[Pattern] to provide the following to {@link [Object]}:</p>
 * <ul>
 * <li></li>
 * </ul>
 * <p>Example:</p>
 * <pre>
 * </pre>
 *
 * @since 0.0.1
 */
public class MongoUtils {
  public static <T, K> JacksonDBCollection<T, K> getInitializedJacksonCollection(
          DB mongoDb,
          String collectionName,
          Class<T> collectionType,
          Class<K> keyType) {
    DBCollection collection = mongoDb.getCollection(collectionName);
    if (collection == null) {
      throw new ReadServiceInitializationException("Collection is null. " +
              "Be sure your mongodb instance has a collection named: '" + collectionName + "'");
    }

    try {
      ObjectMapper mapper = getObjectMapper();
      return JacksonDBCollection.wrap(
              collection,
              collectionType,
              keyType,
              mapper);
    } catch (Exception e) {
      throw new ReadServiceInitializationException("Failed to initialize collection. " +
              "Cause: " + e +
              "Be sure your mongodb instance has a collection named: '" + collectionName + "'", e);
    }
  }

  private static ObjectMapper getObjectMapper() {
    ObjectMapper mapper = new ObjectMapper();
    registerObjectMapperModules(mapper);
    return mapper;
  }

  private static void registerObjectMapperModules(ObjectMapper mapper) {
    mapper.registerModule(new JodaModule());
  }
}
