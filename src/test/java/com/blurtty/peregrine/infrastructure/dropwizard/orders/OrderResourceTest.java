package com.blurtty.peregrine.infrastructure.dropwizard.orders;

import com.blurtty.peregrine.infrastructure.dropwizard.resources.OrderResource;
import com.blurtty.peregrine.service.ApplicationService;
import com.blurtty.peregrine.testing.web.BaseResourceTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;

public class OrderResourceTest extends BaseResourceTest {

  private final ApplicationService appService = mock(ApplicationService.class);
  private final OrderResource orderResource = new OrderResource(appService);

  @Before
  public void setUp() throws IOException {
  }

  @After
  public void tearDown() {
    reset(appService);
  }

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Test
  public void testAddBidOrder() {

    // Arrange

    // Act

    // Assert

  }

}
