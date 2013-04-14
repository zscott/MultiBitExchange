package com.blurtty.peregrine.infrastructure.dropwizard.common;

/**
 * <p>Model to provide the following to views:<br>
 * <ul>
 * <li>Provision of OAuth2 specific details</li>
 * </ul>
 * </p>
 *
 * @since 0.0.1
 *
 */
public class OAuth2Model extends BaseModel {

  private final String authorizationUrl;

  public OAuth2Model(String authorizationUrl) {
    this.authorizationUrl = authorizationUrl;
  }

  public String getAuthorizationUrl() {
    return authorizationUrl;
  }
}
