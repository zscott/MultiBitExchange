package org.multibit.common;

import java.util.Locale;
import org.joda.time.DateTime;
import org.joda.time.DateTimeUtils;
import org.joda.time.DateTimeZone;
import org.junit.Test;


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

//  @Test
//  public void testFriendlyFormatThaiLocale() {
//    DateTimeUtils.setCurrentMillisFixed(MIDNIGHT_JAN_1_2000_UTC.getMillis());
//
//    // fixme - ZS - test failing on Macbook Pro
//    // [~/projects/MultiBitExchange :(   (develop)]  java -version
//    // java version "1.6.0_33"
//    // Java(TM) SE Runtime Environment (build 1.6.0_33-b03-424-10M3720)
//    // Java HotSpot(TM) 64-Bit Server VM (build 20.8-b03-424, mixed mode)
//    // assertEquals("วันเสาร์, มกราคม 01", DateUtils.formatFriendlyDate(DateUtils.nowUtc(), new Locale("th","TH","TH")));
//  }

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
