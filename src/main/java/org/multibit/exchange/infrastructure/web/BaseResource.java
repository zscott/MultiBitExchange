package org.multibit.exchange.infrastructure.web;

import java.util.Locale;
import javax.inject.Inject;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.UriInfo;
import org.multibit.exchange.infrastructure.guice.annotation.DefaultLocale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Abstract base class to provide the following to subclasses:</p>
 * <ul>
 * <li>Provision of common methods</li>
 * </ul>
 *
 * @since 0.0.1
 *         
 */
public class BaseResource {

  private static final Logger log = LoggerFactory.getLogger(BaseResource.class);

  /**
   * Default locale. Used as a fall-back if locale cannot be determined otherwise.
   */
  @Inject
  @DefaultLocale
  protected Locale defaultLocale;

  /**
   * Jersey guarantees request scope
   */
  @Context
  protected UriInfo uriInfo;

  /**
   * Jersey guarantees request scope
   */
  @Context
  protected HttpHeaders httpHeaders;

  /**
   * Jersey guarantees request scope
   */
  //@Context
  //protected HttpServletRequest request;

  /**
   * @return The most appropriate locale for the upstream request (never null)
   */
  public Locale getLocale() {
    Locale locale;
    if (httpHeaders == null) {
      locale = defaultLocale;
    } else {
      locale = httpHeaders.getLanguage();
      if (locale == null) {
        locale = defaultLocale;
      }
    }
    return locale;
  }

  /**
   * @param httpHeaders The request scoped HTTP headers
   */
  /* package */ void setHttpHeaders(HttpHeaders httpHeaders) {
    this.httpHeaders = httpHeaders;
  }
}