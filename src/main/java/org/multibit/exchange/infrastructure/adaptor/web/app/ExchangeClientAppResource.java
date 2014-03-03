package org.multibit.exchange.infrastructure.adaptor.web.app;

import com.yammer.dropwizard.jersey.caching.CacheControl;
import com.yammer.metrics.annotation.Timed;
import org.multibit.exchange.infrastructure.adaptor.web.app.view.AppView;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/")
@Produces(MediaType.TEXT_HTML)
public class ExchangeClientAppResource {

  @GET
  @Timed
  @CacheControl(noCache = true)
  public AppView getApp() {
    return new AppView();
  }
}
