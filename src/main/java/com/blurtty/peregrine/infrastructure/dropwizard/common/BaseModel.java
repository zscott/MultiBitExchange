package com.blurtty.peregrine.infrastructure.dropwizard.common;

/**
 * <p>Base class to provide the following to views:</p>
 * <ul>
 * <li>Access to common data (user, adverts etc)</li>
 *
 * @since 0.0.1
 *         
 */
public class BaseModel {

  // TODO Add user, adverts etc

  private String message;

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
