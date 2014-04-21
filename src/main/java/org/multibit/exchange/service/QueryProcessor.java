package org.multibit.exchange.service;

import org.multibit.exchange.infrastructure.adaptor.eventapi.CurrencyPairId;
import org.multibit.exchange.infrastructure.adaptor.web.restapi.readmodel.CurrencyPairReadModel;
import org.multibit.exchange.infrastructure.adaptor.web.restapi.readmodel.OrderBookReadModel;
import org.multibit.exchange.infrastructure.adaptor.web.restapi.readmodel.QuoteReadModel;
import org.multibit.exchange.presentation.model.marketdepth.MarketDepthPresentationModel;

import java.util.List;

/**
 * <p>Provides read-only access to various models.</p>
 *
 * @since 0.0.1
 *  
 */
public interface QueryProcessor {
  List<CurrencyPairReadModel> fetchCurrencyPairs(String exchangeId);

  List<QuoteReadModel> fetchQuotes(String exchangeId);

  OrderBookReadModel fetchOrderBook(String exchangeId, String tickerSymbol);

  MarketDepthPresentationModel fetchMarketDepth(String exchangeIdCode, CurrencyPairId currencyPairId);
}
