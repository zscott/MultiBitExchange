package org.multibit.common;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * <p>Utility to provide the following to all layers:</p>
 * <ul>
 * <li>Exception related utility methods</li>
 * </ul>
 *
 * @since 0.0.1
 */
public class ExceptionUtils {

  public static String getPrettyStackTrace(Throwable t) {
    StringWriter sw = new StringWriter();
    t.printStackTrace(new PrintWriter(sw));
    return sw.toString();
  }
}
