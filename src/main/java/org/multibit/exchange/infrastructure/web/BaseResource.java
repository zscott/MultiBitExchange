package org.multibit.exchange.infrastructure.web;

import org.multibit.exchange.infrastructure.adaptor.api.readmodel.ReadService;
import org.multibit.exchange.infrastructure.common.DefaultLocale;
import org.multibit.exchange.service.ApiService;

import javax.inject.Inject;
import javax.inject.Singleton;
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

  /**
   * Service that provides all the functionality of the exchange platform.
   */
  protected ApiService apiService;

  /**
   * Service that fetches data from read models.
   */
  protected ReadService readService;

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


  public BaseResource(ApiService apiService, ReadService readService) {
    this.apiService = apiService;
    this.readService = readService;
  }

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