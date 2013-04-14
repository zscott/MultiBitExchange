package com.blurtty.peregrine.infrastructure.dropwizard.orders;

import com.blurtty.peregrine.service.DefaultApplicationService;
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

  private final DefaultApplicationService appService = mock(DefaultApplicationService.class);

  @Before
  public void setUp() throws IOException {
  }

  @After
  public void tearDown() {
    reset(appService);
  }

  @Rule
  public ExpectedException thrown = ExpectedException.none();


  /*
   * getDefaultIssueList
   */

  @Test
  public void test() {

    // Arrange

    // Act

    // Assert

  }

}
