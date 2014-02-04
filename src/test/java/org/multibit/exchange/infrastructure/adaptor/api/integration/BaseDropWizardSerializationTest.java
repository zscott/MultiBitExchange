package org.multibit.exchange.infrastructure.adaptor.api.integration;

import com.yammer.dropwizard.testing.FixtureHelpers;
import com.yammer.dropwizard.testing.JsonHelpers;

import java.io.IOException;

/**
 * <p>Base test class to provide the following to tests:</p>
 * <ul>
 * <li>Dropwizard serialization/deserialization test utilities</li>
 * </ul>
 *
 * @since 0.0.1
 *         
 */
public class BaseDropWizardSerializationTest {

  protected Object deserializeFromJsonFixtureFile(String fixturePath, Class type) throws IOException {
    return JsonHelpers.fromJson(FixtureHelpers.fixture("fixtures/ " + fixturePath), type);
  }

  protected Object deserializeFromJson(String json, Class type) throws IOException {
    return JsonHelpers.fromJson(json, type);
  }
}
