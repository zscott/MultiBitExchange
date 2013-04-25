package com.blurtty.peregrine.testing.web;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import java.util.List;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import org.joda.time.DateTime;
import org.joda.time.DateTimeUtils;
import org.joda.time.DateTimeZone;


import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * A base test class for testing resources against a simulated container
 */
public abstract class BaseResourceTest {

  protected HttpHeaders httpHeaders;

  private static final DateTime JAN_1_2000_MIDNIGHT_UTC = new DateTime(2000, 1, 1, 0, 0, 0, 0).withZone(DateTimeZone.UTC);

  static {
    // Fix all DateTime values to midnight on January 01 2000 UTC
    DateTimeUtils.setCurrentMillisFixed(JAN_1_2000_MIDNIGHT_UTC.getMillis());
  }

  /**
   * @param acceptableMediaTypes An optional list of acceptable media types with default of JSON
   */
  protected void setUpHttpHeaders(Optional<List<MediaType>> acceptableMediaTypes) {
    if (!acceptableMediaTypes.isPresent()) {
      List<MediaType> defaultAcceptableMediaTypes= Lists.newArrayList();
      defaultAcceptableMediaTypes.add(MediaType.APPLICATION_JSON_TYPE);
      acceptableMediaTypes= Optional.of(defaultAcceptableMediaTypes);
    }
    httpHeaders = mock(HttpHeaders.class);
    when(httpHeaders.getAcceptableMediaTypes()).thenReturn(acceptableMediaTypes.get());
  }
}