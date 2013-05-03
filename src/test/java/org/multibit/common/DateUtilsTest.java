package org.multibit.common;

import java.util.Locale;
import java.util.TimeZone;

import org.joda.time.DateTime;
import org.joda.time.DateTimeUtils;
import org.joda.time.DateTimeZone;
import org.junit.Test;


import static org.junit.Assert.assertEquals;

public class DateUtilsTest {

  public static final DateTime MIDNIGHT_JAN_1_2000_UTC = new DateTime(2000, 1, 1, 0, 0, 0, 0).withZone(DateTimeZone.UTC);

  @Test
  public void testFriendlyFormatDefaultLocale() {
    DateTimeUtils.setCurrentMillisFixed(MIDNIGHT_JAN_1_2000_UTC.getMillis());

    assertEquals("Saturday, January 01", DateUtils.formatFriendlyDate(DateUtils.nowUtc()));
  }

  @Test
  public void testFriendlyFormatFrenchLocale() {
    DateTimeUtils.setCurrentMillisFixed(MIDNIGHT_JAN_1_2000_UTC.getMillis());

    assertEquals("samedi, janvier 01", DateUtils.formatFriendlyDate(DateUtils.nowUtc(), Locale.FRANCE));
  }

  @Test
  public void testFriendlyFormatThaiLocale() {
    DateTimeUtils.setCurrentMillisFixed(MIDNIGHT_JAN_1_2000_UTC.getMillis());

    assertEquals("วันเสาร์, มกราคม 01", DateUtils.formatFriendlyDate(DateUtils.nowUtc(), new Locale("th","TH","TH")));
  }

  @Test
  public void testISO8601DefaultLocale() {
    DateTime instant = DateUtils.parseISO8601("2000-01-01T12:00:00Z");
    assertEquals("2000-01-01T12:00:00Z",DateUtils.formatISO8601(instant));
  }
}
