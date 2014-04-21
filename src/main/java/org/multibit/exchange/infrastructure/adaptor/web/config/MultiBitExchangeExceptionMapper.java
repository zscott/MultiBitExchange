package org.multibit.exchange.infrastructure.adaptor.web.config;

import org.axonframework.commandhandling.CommandExecutionException;
import org.axonframework.repository.AggregateNotFoundException;
import org.multibit.common.ExceptionUtils;
import org.multibit.exchange.domain.model.DuplicateCurrencyPairSymbolException;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.List;

/**
 * <p>ExceptionMapper to provide the following to the REST API:</p>
 * <ul>
 * <li>MultiBitExchange specific exception mapping.</li>
 * </ul>
 *
 * @since 0.0.1
 */
@Provider
public class MultiBitExchangeExceptionMapper implements ExceptionMapper<RuntimeException> {

  @Context
  private HttpHeaders headers;

  @Override
  public Response toResponse(RuntimeException exception) {
    if (isWrappedException(exception)) {
      return buildErrorInfoResponse(exception.getCause());
    } else {
      return buildErrorInfoResponse(exception);
    }
  }

  private boolean isWrappedException(RuntimeException exception) {
    return CommandExecutionException.class.isAssignableFrom(exception.getClass());
  }

  private Response buildErrorInfoResponse(Throwable throwable) {
    return Response
        .status(getStatus(throwable))
        .entity(getEntity(throwable))
        .type(MediaType.APPLICATION_JSON_TYPE)
        .build();
  }

  private Object getEntity(Throwable exception) {

    // todo - replace ErrorInfo with a factory that changes depending on environment
    // (i.e., show less technical more user-friendly messages in production)
    return new ErrorInfo(
        exception.getClass(),
        exception.getMessage(),
        ExceptionUtils.getPrettyStackTrace(exception),
        exception.getCause());

  }

  private int getErrorCode(Throwable exception) {
    return 0;
  }

  private Response.Status getStatus(Throwable exception) {
    Class<? extends Throwable> type = exception.getClass();

    Response.Status status = Response.Status.INTERNAL_SERVER_ERROR;
    if (AggregateNotFoundException.class.isAssignableFrom(type)) {
      status = Response.Status.NOT_FOUND;
    } else if (IllegalArgumentException.class.isAssignableFrom(type)) {
      status = Response.Status.BAD_REQUEST;
    } else if (DuplicateCurrencyPairSymbolException.class.isAssignableFrom(type)) {
      status = Response.Status.BAD_REQUEST;
    }
    return status;
  }

  /*
   * this doesn't seem to work will with the Postman Chrome Extension (need to explicitly set Accepts headers)
   */
  private Response.ResponseBuilder setContentType(Response.ResponseBuilder rb) {
    List<MediaType> acceptableMediaTypes = headers.getAcceptableMediaTypes();
    if (acceptableMediaTypes != null && acceptableMediaTypes.size() > 0) {
      MediaType m = acceptableMediaTypes.get(0);
      rb = rb.type(m);
    } else {
      rb = rb.type(headers.getMediaType());
    }
    return rb;
  }
}
