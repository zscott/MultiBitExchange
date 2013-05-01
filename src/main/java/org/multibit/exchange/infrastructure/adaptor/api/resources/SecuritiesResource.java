package org.multibit.exchange.infrastructure.adaptor.api.resources;

import com.google.common.collect.Lists;
import com.yammer.dropwizard.jersey.caching.CacheControl;
import com.yammer.metrics.annotation.Timed;
import org.multibit.exchange.infrastructure.adaptor.api.readmodel.OrderListReadModel;
import org.multibit.exchange.infrastructure.adaptor.api.readmodel.OrderReadModel;
import org.multibit.exchange.infrastructure.adaptor.api.readmodel.ReadService;
import org.multibit.exchange.infrastructure.adaptor.api.readmodel.SecurityListReadModel;
import org.multibit.exchange.infrastructure.web.BaseResource;
import org.multibit.exchange.service.ApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * <p>Resource to provide the following to REST clients:</p>
 * <ul>
 * <li>Market related information and functionality</li>
 * </ul>
 *
 * @since 0.0.1
 *         
 */
@Path("/securities")
public class SecuritiesResource extends BaseResource {

  private static Logger LOGGER = LoggerFactory.getLogger(SecuritiesResource.class);

  @Inject
  public SecuritiesResource(ApiService apiService, ReadService readService) {
    super(apiService, readService);
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
  public SecurityListReadModel getSecurities() {
    return new SecurityListReadModel(readService.fetchSecurities());
  }

  /**
   * <p>Places an order</p>
   *
   * @param tickerSymbol    The symbol for the security to place orders for
   * @param orderDescriptor The order details
   */
  @POST
  @Timed
  @CacheControl(noCache = true)
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("/{ticker}/orders")
  public void placeOrder(@PathParam("ticker") String tickerSymbol, OrderDescriptor orderDescriptor) {


  }

  /**
   * <p>Gets open orders</p>
   *
   * @param tickerSymbol    The symbol for the security to fetch orders for
   */
  @GET
  @Timed
  @CacheControl(noCache = true)
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/{ticker}/orders")
  public OrderListReadModel getOpenOrders(@PathParam("ticker") String tickerSymbol) {
    List<OrderReadModel> orderList = readService.fetchOpenOrders(tickerSymbol);
    return new OrderListReadModel(tickerSymbol, orderList);
  }
}
