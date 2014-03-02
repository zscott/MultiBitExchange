package org.multibit.exchange.infrastructure.adaptor.persistence.mongo;

import com.mongodb.DB;
import org.mongojack.JacksonDBCollection;
import org.multibit.exchange.infrastructure.adaptor.web.restapi.readmodel.QuoteReadModel;

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
public class MongoQuoteReadModelRepository extends BaseMongoRepository<QuoteReadModel, String> {
  public MongoQuoteReadModelRepository(DB mongoDb) {
    super(mongoDb, JacksonDBCollection.wrap(
        mongoDb.getCollection(ReadModelCollections.QUOTES),
        QuoteReadModel.class,
        String.class));
  }
}
