package com.blurtty.peregrine.service;

import com.blurtty.peregrine.infrastructure.dropwizard.resources.MarketDescriptor;
import com.blurtty.peregrine.testing.helper.GuiceTestHelper;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * <p>BaseServiceTests provides the following to service tests:</p>
 * <ul>
 *   <li>default test setup and teardown</li>
 *   <li>an initialized instance of appService</li>
 * </ul>
 *
 * @since 0.0.1
 */
public abstract class BaseServiceTest {

  //final ApplicationService appService = GuiceTestHelper.getApplicationService();
  protected ApplicationService appService;

  @Before
  public void setUp() {
    appService = new DefaultApplicationService();
  }

  @Rule
  public ExpectedException thrown = ExpectedException.none();

}
