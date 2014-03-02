package org.multibit.exchange.infrastructure.adaptor.atmosphere;

import org.atmosphere.annotation.Suspend;
import org.atmosphere.cpr.AtmosphereResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

/**
 * <p>StreamResource to provide the following to the public REST API:</p>
 * <ul>
 * <li>JSON/WebSocket streams for trades.</li>
 * </ul>
 *
 * @since 0.0.1
 */
@Path("/trades")
@Produces("application/json")
public class TradesStreamResource {

  private static Logger LOGGER = LoggerFactory.getLogger(TradesStreamResource.class);

  @Suspend(contentType = MediaType.APPLICATION_JSON)
  @GET
  public String suspend(@Context AtmosphereResource resource) {
    resource.setBroadcaster(BroadcastHelper.getTradeBroadcaster());
    return "";
  }
}
