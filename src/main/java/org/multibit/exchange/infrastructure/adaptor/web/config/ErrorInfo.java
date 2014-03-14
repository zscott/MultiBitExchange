package org.multibit.exchange.infrastructure.adaptor.web.config;

/**
 * <p>Data object to provide the following to Dropwizard/Jersey via {@link MultiBitExchangeExceptionMapper}:</p>
 * <ul>
 * <li>A serializable representation of an error/exception.</li>
 * </ul>
 *
 * @since 0.0.1
 */
public class ErrorInfo {

  private final String exception;

  private final String stackTrace;

  private final String cause;

  private final String message;


  public ErrorInfo(Class<? extends Throwable> exceptionClass, String message, String stackTrace, Throwable cause) {
    this.message = message;
    this.stackTrace = stackTrace;
    exception = exceptionClass.getCanonicalName();
    if (cause != null) {
      this.cause = cause.getClass().getCanonicalName();
    } else {
      this.cause = "N/A";
    }
  }

  public String getException() {
    return exception;
  }

  public String getStackTrace() {
    return stackTrace;
  }

  public String getCause() {
    return cause;
  }

  public String getMessage() {
    return message;
  }
}
