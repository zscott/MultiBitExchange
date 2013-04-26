package org.multibit.exchange.infrastructure.adaptor.persistence.mongo;

import com.google.inject.Inject;
import com.mongodb.Mongo;
import com.yammer.dropwizard.lifecycle.Managed;

/**
 * <p>Managed service (see {@link [Managed]} to provide the following to dropwizard:</p>
 * <ul>
 * <li>The ability to start and stop MongoDB</li>
 * </ul>
 *
 * @since 0.0.1
 */
public class MongoDbManaged implements Managed {

  private Mongo mongo;

  @Inject
  public MongoDbManaged(Mongo mongo) {
    this.mongo = mongo;
  }

  public void start() throws Exception {
  }

  public void stop() throws Exception {
    mongo.close();
  }
}
