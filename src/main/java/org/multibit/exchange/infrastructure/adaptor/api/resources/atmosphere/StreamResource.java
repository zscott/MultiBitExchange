package org.multibit.exchange.infrastructure.adaptor.api.resources.atmosphere;

import org.atmosphere.annotation.Suspend;
import org.atmosphere.cpr.BroadcasterFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.util.logging.resources.logging;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * <p>[Pattern] to provide the following to {@link [Object]}:</p>
 * <ul>
 * <li></li>
 * </ul>
 * <p>Example:</p>
 * <pre>
 * </pre>
 *
 * @since 0.0.1
 */
@Path("/stream")
@Produces("application/json")
public class StreamResource {

  private static Logger LOGGER = LoggerFactory.getLogger(StreamResource.class);

  public StreamResource() {

  }

  @Suspend
  @GET
  public String suspend() {
    return "{'status':'ok'}";
  }

  @GET
  @Path("/start")
  public String start() {
    BroadcasterFactory.getDefault().lookup("/*").scheduleFixedBroadcast(
        new Callable<String>() {
          private int count =0;
          @Override
          public String call() throws Exception {

            String message = "{\"message\":\"" + count++ + "\"}\n";
            LOGGER.debug("Pushing message: " + message);
            return message;

          }
        }, 1, TimeUnit.SECONDS);
    return "ok";
  }

}
