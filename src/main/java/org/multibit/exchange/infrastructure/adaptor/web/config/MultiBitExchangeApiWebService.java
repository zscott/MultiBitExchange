package org.multibit.exchange.infrastructure.adaptor.web.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.google.common.base.Preconditions;
import com.google.inject.CreationException;
import com.hubspot.dropwizard.guice.GuiceBundle;
import com.yammer.dropwizard.ConfiguredBundle;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.assets.AssetsBundle;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.config.FilterBuilder;
import com.yammer.dropwizard.config.HttpConfiguration;
import com.yammer.dropwizard.views.ViewBundle;
import org.atmosphere.cpr.ApplicationConfig;
import org.atmosphere.cpr.AtmosphereServlet;
import org.eclipse.jetty.servlets.CrossOriginFilter;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * <p>Service to provide the following to application:</p>
 * <ul>
 * <li>Provision of configuration of dropwizard application</li>
 * </ul>
 * <p>Use <code>java -jar web-develop-SNAPSHOT.jar server mbexchange-demo.yml</code> to start the demo</p>
 *
 * @since 0.0.1
 *  
 */
public class MultiBitExchangeApiWebService extends Service<MultiBitExchangeApiConfiguration> {

  /**
   * The base package for web resources. All sub-packages will be scanned for Jersey Web Resource classes.
   */
  public static final String WEB_RESOURCE_BASE_PACKAGE = "org.multibit.exchange.infrastructure.adaptor.web";
  public static final String ATMOSPHERE_RESOURCE_BASE_PACKAGE = "org.multibit.exchange.infrastructure.adaptor.atmosphere";

  /**
   * The command line arguments to allow DB configuration to take place by Guice
   */
  private String[] args;

  /**
   * Main entry point to the application
   *
   * @param args CLI arguments
   * @throws Exception
   */
  public static void main(String[] args) throws Exception {
    try {
      new MultiBitExchangeApiWebService(args).run(args);
    } catch (CreationException e) {
      System.err.printf("*********%nException on start up - is 'mongod' running and accessible?%n*********%n");
      System.err.printf(String.format("%s:\n%s", e.getClass().getName(), e.getMessage()));
    }
  }

  /**
   * @param args The command line arguments to allow DB configuration to take place by Guice
   */
  private MultiBitExchangeApiWebService(String[] args) {
    this.args = args;
  }

  @Override
  @SuppressWarnings("unchecked")
  public void initialize(Bootstrap<MultiBitExchangeApiConfiguration> bootstrap) {
    bootstrap.setName("multibit-exchange");

    // Configure Guice
    ConfiguredBundle guiceBundle = GuiceBundle
            .newBuilder()
            .addModule(new MultiBitExchangeApiServiceModule(loadConfigurationFromFile(args))) // The main Guice module with bindings
            .enableAutoConfig("org.multibit.exchange.infrastructure.adaptor.web") // Scan application classes
            .build();
    bootstrap.addBundle(guiceBundle);

    // Add asset bundles
    bootstrap.addBundle(new AssetsBundle("/assets/css", "/css"));
    bootstrap.addBundle(new AssetsBundle("/assets/jquery", "/jquery"));
    bootstrap.addBundle(new AssetsBundle("/assets/js", "/js"));
    bootstrap.addBundle(new AssetsBundle("/assets/images", "/images"));

    // Add view bundle
    bootstrap.addBundle(new ViewBundle());
  }

  public MultiBitExchangeApiConfiguration loadConfigurationFromFile(String[] args) {
    Preconditions.checkNotNull(args);
    Preconditions.checkElementIndex(1, args.length);

    // Read the YAML configuration
    ObjectMapper om = new ObjectMapper(new YAMLFactory());
    FileInputStream fis;
    try {
      fis = new FileInputStream(args[1]);
      // Stream will be closed on completion
      return om.readValue(fis, MultiBitExchangeApiConfiguration.class);
    } catch (IOException e) {
      throw new IllegalArgumentException("Cannot read external configuration from '" + args[1] + "'", e);
    }
  }

  @Override
  public void run(MultiBitExchangeApiConfiguration configuration, Environment environment) throws Exception {
    configuration.getHttpConfiguration().setConnectorType(HttpConfiguration.ConnectorType.NONBLOCKING);
    initializeAtmosphere(configuration, environment);
  }

  private void initializeAtmosphere(MultiBitExchangeApiConfiguration configuration, Environment environment) {
    FilterBuilder fconfig = environment.addFilter(CrossOriginFilter.class, "/stream");
    fconfig.setInitParam(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");

    AtmosphereServlet atmosphereServlet = new AtmosphereServlet();
    atmosphereServlet.framework().addInitParameter("com.sun.jersey.config.property.packages", ATMOSPHERE_RESOURCE_BASE_PACKAGE);
    atmosphereServlet.framework().addInitParameter("org.atmosphere.websocket.messageContentType", "application/json");
    atmosphereServlet.framework().addInitParameter("com.sun.jersey.config.feature.DisableWADL", "true");
    atmosphereServlet.framework().addInitParameter("org.atmosphere.cpr.AtmosphereInterceptor.disableDefaults", "true");
    atmosphereServlet.framework().addInitParameter("org.atmosphere.cpr.broadcasterCacheClass", "org.atmosphere.cache.UUIDBroadcasterCache");
    atmosphereServlet.framework().addInitParameter("org.atmosphere.cpr.sessionSupport", "true");
    atmosphereServlet.framework().addInitParameter("org.atmosphere.cpr.AtmosphereResource.uniqueUUID", "true");
    atmosphereServlet.framework().addInitParameter(ApplicationConfig.class.getName() + ".scanClassPath", "false");
    environment.addServlet(atmosphereServlet, "/stream/*");
  }
}
