package org.multibit.exchange.cucumber;

import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * <p>Runs cucumber tests:</p>
 *
 * @since 0.0.1
 */
@RunWith(Cucumber.class)
@Cucumber.Options(strict = true, format = {"pretty", "html:target/cucumber"})
public class RunCukesTest {
}
