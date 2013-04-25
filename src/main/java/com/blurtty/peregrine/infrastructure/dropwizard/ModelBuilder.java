package com.blurtty.peregrine.infrastructure.dropwizard;

import com.google.common.base.Optional;
import java.util.UUID;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.HttpHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blurtty.peregrine.infrastructure.dropwizard.common.BaseModel;

/**
 * <p>Builder to provide the following to resources:</p>
 * <ul>
 * <li>Utility methods to build backing models for Views</li>
 * </ul>
 *
 * @since 0.0.1
 */
public class ModelBuilder {

  private static final Logger log = LoggerFactory.getLogger(ModelBuilder.class);

  public Optional<UUID> extractSessionToken(HttpHeaders httpHeaders) {

    // Want to fail fast to absent() since this will get called a lot
    Optional<UUID> sessionToken = Optional.absent();

    if (httpHeaders == null) {
      // Might be an internal call such as an error condition
      return sessionToken;
    }
    if (httpHeaders.getCookies() == null) {
      // This is a cold user
      return sessionToken;
    }

    Cookie cookie = httpHeaders.getCookies().get(PeregrineConfiguration.SESSION_TOKEN_NAME);
    if (cookie == null) {
      // This is a cold user
      return sessionToken;
    }
    String value = cookie.getValue();
    if (value == null) {
      // This is a broken cookie
      // Rather than throw an error we can force a fresh login to fix it up
      return sessionToken;
    }

    // Must be OK to be here
    return Optional.of(UUID.fromString(value));

  }

  /**
   * @return A new base common with user populated from the session token if present
   */
  public BaseModel newBaseModel(HttpHeaders httpHeaders) {
    BaseModel baseModel = new BaseModel();
    return baseModel;
  }
}
