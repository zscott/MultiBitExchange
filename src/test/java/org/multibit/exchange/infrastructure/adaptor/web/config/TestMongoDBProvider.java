package org.multibit.exchange.infrastructure.adaptor.web.config;

import com.mongodb.DB;
import com.mongodb.Mongo;
import de.flapdoodle.embed.mongo.tests.MongodForTestsFactory;

import java.io.IOException;

public class TestMongoDBProvider implements MongoDBProvider {

  @Override
  public DB get() {
    try {
      MongodForTestsFactory factory = new MongodForTestsFactory();
      Mongo mongo = factory.newMongo();
      return factory.newDB(mongo);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
