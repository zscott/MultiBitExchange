package org.multibit.exchange.infrastructure.adaptor.persistence.mongo;

import com.mongodb.DB;
import java.util.List;
import javax.inject.Inject;
import org.mongojack.JacksonDBCollection;
import org.multibit.exchange.readmodel.SecuritiesReadModelBuilder;
import org.multibit.exchange.readmodel.SecurityReadModel;
import org.multibit.exchange.service.SecuritiesReadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>MongoReadService to provide the following to {@link: SecuritiesReadService}:</p>
 * <ul>
 * <li>A MongoDB implementation</li>
 * </ul>
 *
 * @since 0.0.1
 *         
 */
public class MongoSecuritiesReadService implements SecuritiesReadService {

  private static Logger LOGGER = LoggerFactory.getLogger(MongoSecuritiesReadService.class);

  private final DB db;

  private final SecuritiesReadModelBuilder readModelBuilder;

  private final JacksonDBCollection<SecurityReadModel, String> collection;

  @Inject
  public MongoSecuritiesReadService(DB mongoDb, SecuritiesReadModelBuilder readModelBuilder) {
    this.db = mongoDb;
    this.readModelBuilder = readModelBuilder;
    LOGGER.trace("initilized with ReadModelBuilder: {}", readModelBuilder);

    this.collection = JacksonDBCollection.wrap(
        mongoDb.getCollection(ReadModelCollections.SECURITIES),
        SecurityReadModel.class,
        String.class);
    LOGGER.trace("JacksonDBCollection initialized");
  }

  @Override
  public List<SecurityReadModel> fetchSecurities() {
    return collection.find().toArray();
  }

  @Override
  public String toString() {
    return "MongoSecuritiesReadService{" +
        "db=" + db +
        ", readModelBuilder=" + readModelBuilder +
        ", collection=" + collection +
        '}';
  }
}
