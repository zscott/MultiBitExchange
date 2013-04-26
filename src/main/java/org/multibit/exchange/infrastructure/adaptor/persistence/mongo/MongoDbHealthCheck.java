package org.multibit.exchange.infrastructure.adaptor.persistence.mongo;

import com.google.inject.Inject;
import com.mongodb.Mongo;
import com.yammer.metrics.core.HealthCheck;

/**
 * <p>HealthCheck to provide the following to the application:</p>
 * <ul>
 * <li>Verifies that the connection to MongoDB is healthy</li>
 * <li>Verifies that MongoDB can return its set of database names</li>
 * </ul>
 *
 * @since 0.0.1
 */
public class MongoDbHealthCheck extends HealthCheck {

  private final Mongo mongo;

  @Inject
  public MongoDbHealthCheck(Mongo mongo) {
    super("MongoHealthCheck");
    this.mongo = mongo;
  }

  @Override
  protected Result check() throws Exception {
    verifyConnection();
    return Result.healthy();
  }

  private void verifyConnection() {
    mongo.getDatabaseNames();
  }
}
