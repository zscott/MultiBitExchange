package org.multibit.exchange.infrastructure.db.mongo;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import org.junit.AfterClass;
import org.multibit.exchange.infrastructure.adaptor.persistence.mongo.MongodSandboxFactory;

import java.io.IOException;
import java.util.Set;

/**
 * <p>Base class to provide the following to MongoDB specific tests</p>
 * <ul>
 * <li>A running instance of MongoDB</li>
 * </ul>
 *
 * @since 0.0.1
 *  
 */
public class BaseMongoDbTest {

  private static MongodSandboxFactory factory = null;

  protected static DB db = getNewMongoDB();

  @AfterClass
  public static void afterClass() {
    dropAllCollections(db);
  }

  protected static DB getNewMongoDB() {
    try {
      initFactory();
      Mongo mongo = factory.newMongo();
      return factory.newDB(mongo);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private synchronized static void initFactory() throws IOException {
    if (factory == null)
      factory = new MongodSandboxFactory();
  }

  protected static void shutdownMongoDB() {
    if (factory != null) {
      factory.shutdown();
    }
  }

  protected static void dropAllCollections(DB db) {
    try {
      Set<String> collectionNames = db.getCollectionNames();
      for (String name : collectionNames) {
        DBCollection collection = db.getCollection(name);
        try {
          collection.drop();
        } catch (RuntimeException e) {
          // ignore
        }
      }
    } catch (MongoException e) {
      // can happen when this is called while Mongo is being shut down.
    }
  }
}
