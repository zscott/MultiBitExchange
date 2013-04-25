package org.multibit.exchange.infrastructure.dropwizard;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yammer.dropwizard.client.JerseyClientConfiguration;
import com.yammer.dropwizard.config.Configuration;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * <p>DropWizard Configuration to provide the following to application:</p>
 * <ul>
 * <li>Initialisation code</li>
 * </ul>
 *
 * @since 0.0.1
 *         
 */
public class MultiBitExchangeConfiguration extends Configuration {

  /**
   * The cookie name for the session token
   */
  public static final String SESSION_TOKEN_NAME ="MBEXCHANGE-Session";

  /**
   * The cookie name for the "remember me" token
   */
  private static final String rememberMeName ="MBEXCHANGE-RememberMe";

  @Valid
  @NotNull
  @JsonProperty
  private JerseyClientConfiguration httpClient = new JerseyClientConfiguration();

  @NotEmpty
  @JsonProperty
  private String mongoUri;

  public String getMongoUri() {
    return mongoUri;
  }
}

