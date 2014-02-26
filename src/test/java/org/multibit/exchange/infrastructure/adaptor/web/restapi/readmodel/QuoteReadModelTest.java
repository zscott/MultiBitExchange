package org.multibit.exchange.infrastructure.adaptor.web.restapi.readmodel;

import org.bson.types.ObjectId;
import org.junit.Test;
import org.multibit.common.DateUtils;
import org.multibit.exchange.testing.ExchangeIdFaker;
import org.multibit.exchange.testing.ItemPriceFaker;
import org.multibit.exchange.testing.TickerFaker;

import static org.fest.assertions.api.Assertions.assertThat;

public class QuoteReadModelTest {

  @Test
  public void createValid() {
    // Arrange
    String _id = new ObjectId().toString();
    String exchangeId = ExchangeIdFaker.createValid().getCode();
    String ticker = TickerFaker.createValid().getSymbol();
    String bid = ItemPriceFaker.createValid().getRaw();
    String ask = ItemPriceFaker.createValid().getRaw();
    String timestamp = DateUtils.formatISO8601(DateUtils.nowUtc());

    // Act
    QuoteReadModel quoteReadModel = new QuoteReadModel(_id, exchangeId, ticker, bid, ask, timestamp);

    // Assert
    assertThat(quoteReadModel.getId()).isEqualTo(_id);
    assertThat(quoteReadModel.getExchangeId()).isEqualTo(exchangeId);
    assertThat(quoteReadModel.getTicker()).isEqualTo(ticker);
    assertThat(quoteReadModel.getBid()).isEqualTo(bid);
    assertThat(quoteReadModel.getAsk()).isEqualTo(ask);
    assertThat(quoteReadModel.getTimestamp()).isEqualTo(timestamp);
  }

}
