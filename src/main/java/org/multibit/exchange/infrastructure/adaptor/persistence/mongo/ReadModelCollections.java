package org.multibit.exchange.infrastructure.adaptor.persistence.mongo;

/**
 * <p>A utility class to provide the following to the infrastructure.persistence.mongo layer:</p>
 * <ul>
 * <li>MongoDB collections used to back ReadModels</li>
 * </ul>
 *
 * @since 0.0.1
 *  
 */
public class ReadModelCollections {

  public static final String SECURITIES = "securities_readmodel";

  public static final String QUOTES = "quotes_readmodel";

  public static final String ORDER_BOOKS = "orderbooks_readmodel";
}
