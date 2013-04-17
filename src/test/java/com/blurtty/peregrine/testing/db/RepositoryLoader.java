package com.blurtty.peregrine.testing.db;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.mongodb.DB;
import com.mongodb.MongoURI;

import com.blurtty.peregrine.infrastructure.dropwizard.PeregrineConfiguration;


import java.io.FileInputStream;
import java.io.IOException;

/**
 * <p>Utility to provide the following to application:</p>
 * <ul>
 * <li>Population of test data into the repository</li>
 * </ul>
 * <p>Example</p>
 * <pre>
 *   java RepositoryLoader cap-demo.yml
 * </pre>
 *
 * @since 0.0.1
 *         
 */
public class RepositoryLoader {

  /**
   * @param args [0]: The name of the YAML configuration file
   *
   * @throws IOException
   */
  public static void main(String[] args) throws IOException {

    if (args == null || args.length != 1) {
      System.out.println("ERROR: Require path to configuration as first argument.");
      System.exit(1);
    }

    // Read the YAML configuration
    ObjectMapper om = new ObjectMapper(new YAMLFactory());
    FileInputStream fis = new FileInputStream(args[0]);
    PeregrineConfiguration configuration = om.readValue(fis, PeregrineConfiguration.class);

    // MongoDb Setup
    MongoURI mongoUri = new MongoURI(configuration.getMongoUri());
    DB db = mongoUri.connectDB();
    if (mongoUri.getUsername() != null && mongoUri.getPassword() != null) {
      db.authenticate(mongoUri.getUsername(), mongoUri.getPassword());
    }

    // One-off populations to avoid duplicates
    if (!db.collectionExists("issues")) {
      db.createCollection("issues",null);
      createIssues(db);
    }
    if (!db.collectionExists("users")) {
      db.createCollection("users",null);
      createUsers(db);
    }

  }

  private static void createUsers(DB db) {
//    UserRepository userRepository = new MongoUserRepository(db);
//    List<User> users = UserFaker.createOpenIDUsers("test", 100);
//    List<String> ids = userRepository.createAll(users);
//    System.out.printf("Created %d issues.%n",ids.size());
  }

  private static void createIssues(DB db) {
//    IssueRepository issueRepository = new MongoIssueRepository(db);
//    List<Issue> issues = IssueFaker.createIssues("test", 100);
//    List<String> ids = issueRepository.createAll(issues);
//    System.out.printf("Created %d issues.%n",ids.size());
  }


}
