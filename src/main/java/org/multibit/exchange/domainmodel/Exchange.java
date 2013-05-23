package org.multibit.exchange.domainmodel;

import com.google.common.collect.Maps;
import java.util.Map;

/**
 * <p>An exchange containing many securities.</p>
 *
 * @since 0.0.1
 */
public class Exchange {

  private Map<Ticker, Security> securities = Maps.newHashMapWithExpectedSize(10);

  public void addSecurity(Ticker ticker, CurrencyPair currencyPair) throws DuplicateTickerException {
    if (securities.containsKey(ticker))
      throw new DuplicateTickerException(ticker);

    securities.put(ticker, new Security(ticker, currencyPair));
  }

  public void removeSecurity(Ticker ticker) throws NoSuchTickerException {
    if (!securities.containsKey(ticker))
      throw new NoSuchTickerException(ticker);

    securities.remove(ticker);
  }
}
