package org.multibit.exchange.infrastructure.adaptor.web.restapi.resources;

import com.yammer.dropwizard.jersey.caching.CacheControl;
import com.yammer.metrics.annotation.Timed;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
public class RootResource {

  /**
   * <p>Fetches list of securities from the read model</p>
   *
   * @return The list of securities
   */
  @GET
  @Timed
  @CacheControl(noCache = true)
  @Produces(MediaType.APPLICATION_JSON)
  public Response get() {
    // todo - add API documentation to the API root
    return Response.status(200).entity("{\"status\":\"ok\"}").build();
  }
}
