package com.blurtty.peregrine.infrastructure.dropwizard.common;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.blurtty.peregrine.infrastructure.dropwizard.ModelBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.UriInfo;
import java.util.Locale;

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
   * Thread-safe common builder instance
   */
  @Inject
  protected ModelBuilder modelBuilder;

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
  @Context
  protected HttpServletRequest request;

  /**
   * @return The most appropriate locale for the upstream request (never null)
   */
  public Locale getLocale() {
    // TODO This should be a configuration setting
    Locale defaultLocale = Locale.UK;

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