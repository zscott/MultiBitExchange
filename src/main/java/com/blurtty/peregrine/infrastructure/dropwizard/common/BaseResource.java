package com.blurtty.peregrine.infrastructure.dropwizard.common;

import java.util.Locale;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.UriInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blurtty.peregrine.infrastructure.dropwizard.ModelBuilder;
import com.blurtty.peregrine.infrastructure.guice.annotation.DefaultLocale;

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