package org.multibit.exchange.testing;

import com.github.javafaker.Faker;

/**
 * <p>Faker to provide the following to tests:</p>
 * <ul>
 * <li>Fake instances of a broker identifier string.</li>
 * </ul>
 *
 * @since 0.0.1
 *         
 */
public class BrokerFaker {

  static Faker faker = new Faker();

  public static String createValid() {
    return faker.bothify("BRK-####-?#")
        + "-" + faker.firstName().toLowerCase()
        + "-" + faker.lastName().toLowerCase();
  }
}
