package org.multibit.exchange.infrastructure.adaptor.api.resources;

import com.yammer.dropwizard.jersey.caching.CacheControl;
import com.yammer.metrics.annotation.Timed;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.multibit.exchange.infrastructure.web.BaseResource;
import org.multibit.exchange.readmodel.SecurityListReadModel;
import org.multibit.exchange.service.ApiService;
import org.multibit.exchange.service.SecuritiesReadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Resource to provide the following to the application:</p>
 * <ul>
 * <li>Security related information and functionality</li>
 * </ul>
 *
 * @since 0.0.1
 *         
 */
@Path("/market")
public class MarketResource extends BaseResource {

  private static Logger LOGGER = LoggerFactory.getLogger(MarketResource.class);

  private final ApiService apiService;

  private final SecuritiesReadService securitiesReadService;

  @Inject
  @Singleton
  public MarketResource(ApiService apiService, SecuritiesReadService securitiesReadService) {
    LOGGER.debug("initialized with {} and {}", apiService, securitiesReadService);
    this.apiService = apiService;
    this.securitiesReadService = securitiesReadService;
  }

  /**
   * <p>Creates a new security</p>
   *
   * @param securityDescriptor The properties of the security
   */
  @POST
  @Timed
  @CacheControl(noCache = true)
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("securities")
  public void addSecurity(SecurityDescriptor securityDescriptor) {
    apiService.createSecurity(
        securityDescriptor.getTickerSymbol(),
        securityDescriptor.getTradeableItemSymbol(),
        securityDescriptor.getCurrencySymbol());
  }

  /**
   * <p>Fetches list of securities from the read model</p>
   *
   * @return The list of securities
   */
  @GET
  @Timed
  @CacheControl(noCache = true)
  @Produces(MediaType.APPLICATION_JSON)
  @Path("securities")
  public SecurityListReadModel getSecurities() {
    return new SecurityListReadModel(securitiesReadService.fetchSecurities());
  }
}
