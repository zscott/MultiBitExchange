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

  public static final String CURRENCY_PAIRS = "currency_pairs";

  public static final String QUOTES = "quotes";

  public static final String ORDERBOOKS = "orderbooks";

  public static final String MARKET_DEPTH = "marketdepth";
}
