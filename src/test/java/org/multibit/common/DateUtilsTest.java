package org.multibit.common;

import org.joda.time.DateTime;
import org.joda.time.DateTimeUtils;
import org.joda.time.DateTimeZone;
import org.junit.Test;

import java.util.Locale;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

public class DateUtilsTest {

  @Test
  public void testFriendlyFormatDefaultLocale() {
    // Arrange
    DateTime dateTime = DateUtils.thenUtc(2000, 1, 1, 0, 0, 0);
    String expectedDateString = "Saturday, January 01";

    // Act
    String actualDateString = DateUtils.formatFriendlyDate(dateTime);

    // Assert
    assertEquals(expectedDateString, actualDateString);
  }

  @Test
  public void testFriendlyFormatFrenchLocale() {
    // Arrange
    Locale locale = Locale.FRANCE;
    DateTime dateTime = DateUtils.thenUtc(2000, 1, 1, 0, 0, 0);
    String expectedDateString = "samedi, janvier 01";

    // Act
    String actualDateString = DateUtils.formatFriendlyDate(dateTime, locale);

    // Assert
    assertEquals(expectedDateString, actualDateString);
  }

  @Test
  public void testFriendlyFormatThaiLocale() {
    // Arrange
    Locale locale = new Locale("th", "TH", "TH");
    DateTime dateTime = DateUtils.thenUtc(2000, 1, 1, 0, 0, 0);
    String expectedDateString = "วันเสาร์, มกราคม 01";

    // Act
    String actualDateString = DateUtils.formatFriendlyDate(dateTime, locale);

    // Assert
    assertEquals(expectedDateString, actualDateString);
  }

  @Test
  public void testDateUtilsNowUtc() {
    // Arrange
    DateTime dateTime = new DateTime(2000, 1, 1, 0, 0, 0, 0).withZone(DateTimeZone.UTC);
    DateTimeUtils.setCurrentMillisFixed(dateTime.getMillis());
    DateTime expectedDateTime = dateTime;

    // Act
    DateTime actualDateTime = DateUtils.nowUtc();

    // Assert
    assertThat(actualDateTime).isEqualTo(expectedDateTime);
  }


  @Test
  public void testISO8601DefaultLocale() {
    DateTime instant = DateUtils.parseISO8601("2000-01-01T12:00:00Z");
    assertEquals("2000-01-01T12:00:00Z", DateUtils.formatISO8601(instant));
  }
}
