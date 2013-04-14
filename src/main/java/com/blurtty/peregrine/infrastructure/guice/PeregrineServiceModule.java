package com.blurtty.peregrine.infrastructure.guice;

import com.blurtty.peregrine.infrastructure.dropwizard.PeregrineConfiguration;
import com.blurtty.peregrine.infrastructure.dropwizard.resources.OrderReadService;
import com.blurtty.peregrine.infrastructure.persistence.mongo.MongoOrderReadService;
import com.blurtty.peregrine.service.ApplicationService;
import com.blurtty.peregrine.service.DefaultApplicationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.google.common.base.Preconditions;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoURI;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.UnknownHostException;

/**
 * <p>Guice module to provide the following to application:</p>
 * <ul>
 * <li>Bindings between injected interfaces and their implementations</li>
 * </ul>
 *
 * @since 0.0.1
 *         
 */
public class PeregrineServiceModule extends AbstractModule {

  private final PeregrineConfiguration configuration;

  public PeregrineServiceModule(String[] args) {

    Preconditions.checkNotNull(args);
    Preconditions.checkElementIndex(1, args.length);

    // Read the YAML configuration
    ObjectMapper om = new ObjectMapper(new YAMLFactory());
    FileInputStream fis;
    try {
      fis = new FileInputStream(args[1]);
      // Stream will be closed on completion
      this.configuration = om.readValue(fis, PeregrineConfiguration.class);
    } catch (IOException e) {
      throw new IllegalArgumentException("Cannot read external configuration from '" + args[1] + "'", e);
    }
  }

  @Override
  protected void configure() {

    // Application support
    bind(ApplicationService.class).to(DefaultApplicationService.class).asEagerSingleton();

    // Read Services
    bind(OrderReadService.class).to(MongoOrderReadService.class).asEagerSingleton();

  }

  @Provides
  @Singleton
  public Mongo getMongo() {

    return getDB().getMongo();

  }

  @Provides
  @Singleton
  public DB getDB() {

    // MongoDB Setup
    final MongoURI mongoUri = new MongoURI(configuration.getMongoUri());
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
      // Check that a collection can be reached anonymously instead
      db.getCollection("orders").count();
    }
    return db;

  }
}
