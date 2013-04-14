package com.blurtty.peregrine.service;

import com.blurtty.peregrine.infrastructure.dropwizard.resources.MarketDescriptor;
import com.blurtty.peregrine.testing.helper.GuiceTestHelper;
import com.google.common.eventbus.EventBus;
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

  protected EventBus eventBus;

  protected ApplicationService appService;

  @Before
  public void setUp() {
    eventBus = new EventBus();
    appService = new DefaultApplicationService(eventBus);
  }

  @Rule
  public ExpectedException thrown = ExpectedException.none();

}
