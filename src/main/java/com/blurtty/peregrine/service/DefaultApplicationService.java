package com.blurtty.peregrine.service;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Concrete implementation to provide the following to ApplicationService:</p>
 * <ul>
 * <li>Default implementation using Repositories, and GitHub</li>
 * </ul>
 *
 * @since 0.0.1
 */
public class DefaultApplicationService implements ApplicationService {

  private static final Logger log = LoggerFactory.getLogger(DefaultApplicationService.class);

  @Inject
  public DefaultApplicationService() {
  }

}
