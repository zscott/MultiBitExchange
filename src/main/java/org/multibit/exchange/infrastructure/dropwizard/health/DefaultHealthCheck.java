package org.multibit.exchange.infrastructure.dropwizard.health;

import com.yammer.metrics.core.HealthCheck;

/**
 * <p>HealthCheck to provide the following to application:</p>
 * <ul>
 * <li>Provision of checks against a given Configuration property </li>
 * </ul>
 *
 * @since 0.0.1
 *         
 */
public class DefaultHealthCheck extends HealthCheck {

  public DefaultHealthCheck() {
    super("OpenID Demo");
  }

  @Override
  protected Result check() throws Exception {
    return Result.healthy();
  }
}