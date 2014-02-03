package org.multibit.exchange.infrastructure.adaptor.api.resources.atmosphere;

import org.atmosphere.annotation.Suspend;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * <p>Resource to provide the following to the public REST API:</p>
 * <ul>
 * <li>JSON/WebSocket streams for: trades, orderbook events, and quotes.</li>
 * </ul>
 *
 * @since 0.0.1
 */
@Path("/")
@Produces("application/json")
public class StreamResource {

  @Suspend
  @Path("/trades")
  @GET
  public String suspend() {
    return "";
  }

}
