package org.multibit.exchange.infrastructure.adaptor.web.config;

import com.google.common.base.Preconditions;
import com.mongodb.DB;
import com.mongodb.MongoURI;
import org.multibit.exchange.infrastructure.adaptor.persistence.mongo.ReadModelCollections;

import java.net.UnknownHostException;

public class ProductionMongoDBProvider implements MongoDBProvider {

  private MultiBitExchangeApiConfiguration configuration;

  public ProductionMongoDBProvider(MultiBitExchangeApiConfiguration configuration) {
    this.configuration = configuration;
  }

  @Override
  public DB get() {
    String mongoUriString = configuration.getMongoUri();
    Preconditions.checkState(mongoUriString != null, "mongouri not specified in configuraiton");

    final MongoURI mongoUri = new MongoURI(mongoUriString);
    final DB db;
    try {
      db = mongoUri.connectDB();
    } catch (UnknownHostException e) {
      throw new IllegalArgumentException("Cannot connect to MongoDB", e);
    }

    // Authenticate
    if (mongoUri.getUsername() != null && mongoUri.getPassword() != null) {
      db.authenticate(mongoUri.getUsername(), mongoUri.getPassword());
    } else {
      // Check that collections can be reached anonymously instead
      db.getCollection(ReadModelCollections.SECURITIES).count();
      db.getCollection(ReadModelCollections.QUOTES).count();
      db.getCollection(ReadModelCollections.ORDER_BOOKS).count();
    }
    return db;
  }
}
