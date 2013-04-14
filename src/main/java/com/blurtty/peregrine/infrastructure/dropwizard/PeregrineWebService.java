package com.blurtty.peregrine.infrastructure.dropwizard;

import com.blurtty.peregrine.infrastructure.guice.PeregrineServiceModule;
import com.google.inject.CreationException;
import com.hubspot.dropwizard.guice.GuiceBundle;
import com.yammer.dropwizard.ConfiguredBundle;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.assets.AssetsBundle;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.views.ViewBundle;

/**
 * <p>Service to provide the following to application:</p>
 * <ul>
 * <li>Provision of configuration of dropwizard application</li>
 * </ul>
 * <p>Use <code>java -jar web-develop-SNAPSHOT.jar server peregrine-demo.yml</code> to start the demo</p>
 *
 * @since 0.0.1
 *         
 */
public class PeregrineWebService extends Service<PeregrineConfiguration> {

  /**
   * The command line arguments to allow DB configuration to take place by Guice
   */
  private String[] args;

  /**
   * Main entry point to the application
   *
   * @param args CLI arguments
   *
   * @throws Exception
   */
  public static void main(String[] args) throws Exception {
    try {
      new PeregrineWebService(args).run(args);
    } catch (CreationException e) {
      System.err.printf("*********%nException on start up - is Mongod running and accessible?%n*********%n");
      System.err.printf(String.format("%s:\n%s", e.getClass().getName(), e.getMessage()));
    }
  }

  /**
   * @param args The command line arguments to allow DB configuration to take place by Guice
   */
  private PeregrineWebService(String[] args) {
    this.args = args;
  }

  @Override
  @SuppressWarnings("unchecked")
  public void initialize(Bootstrap<PeregrineConfiguration> bootstrap) {

    // Configure Guice first
    // TODO The intermediate call to initialize() can be removed after DW 0.6.2+
    // This will fix the unchecked warning
    ConfiguredBundle guiceBundle = GuiceBundle
      .newBuilder()
      .addModule(new PeregrineServiceModule(args)) // The main Guice module with bindings
      .enableAutoConfig(getClass().getPackage().getName()) // Scan application classes
      .build();
    guiceBundle.initialize(bootstrap);
    bootstrap.addBundle(guiceBundle);

    // Add asset bundles
    bootstrap.addBundle(new AssetsBundle("/assets/css", "/css"));
    bootstrap.addBundle(new AssetsBundle("/assets/jquery", "/jquery"));
    bootstrap.addBundle(new AssetsBundle("/assets/images", "/images"));

    // Add view bundle
    bootstrap.addBundle(new ViewBundle());

  }

  @Override
  public void run(PeregrineConfiguration configuration, Environment environment) throws Exception {

    // Add any specialised configuration here

  }
}
