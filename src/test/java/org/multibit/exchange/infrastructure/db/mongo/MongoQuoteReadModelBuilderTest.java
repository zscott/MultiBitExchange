package org.multibit.exchange.infrastructure.db.mongo;

import org.axonframework.domain.GenericDomainEventMessage;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.SimpleEventBus;
import org.joda.time.DateTime;
import org.joda.time.DateTimeUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.multibit.common.DateUtils;
import org.multibit.exchange.domain.command.ExchangeId;
import org.multibit.exchange.domain.event.CurrencyPairRegisteredEvent;
import org.multibit.exchange.domain.model.CurrencyPair;
import org.multibit.exchange.infrastructure.adaptor.persistence.mongo.MongoQueryProcessor;
import org.multibit.exchange.infrastructure.adaptor.persistence.mongo.MongoQuoteReadModelBuilder;
import org.multibit.exchange.infrastructure.adaptor.web.restapi.readmodel.QuoteReadModel;
import org.multibit.exchange.service.QueryProcessor;
import org.multibit.exchange.testing.CurrencyPairFaker;
import org.multibit.exchange.testing.ExchangeIdFaker;

import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;

public class MongoQuoteReadModelBuilderTest extends BaseMongoDbTest {

  private MongoQuoteReadModelBuilder readModelBuilder;
  private QueryProcessor readService;
  private EventBus eventBus = new SimpleEventBus();
  private DateTime fixedDateTime;

  @Before
  public void setUp() {
    fixedDateTime = new DateTime();
    DateTimeUtils.setCurrentMillisFixed(fixedDateTime.getMillis());
    readModelBuilder = new MongoQuoteReadModelBuilder(db, eventBus);
    readService = new MongoQueryProcessor(db);
  }

  @After
  public void tearDown() {
    dropAllCollections(db);
    DateTimeUtils.setCurrentMillisSystem();
  }

  @Test
  public void currencyPairRegistered_OneEvent() {
    // Arrange
    ExchangeId exchangeId = ExchangeIdFaker.createValid();
    String expectedExchangeId = exchangeId.getCode();
    CurrencyPair currencyPair = CurrencyPairFaker.createValid();

    int expectedSize = 1;
    String expectedTickerSymbol = currencyPair.getTicker().getSymbol();
    String expectedBid = null;
    String expectedAsk = null;
    String expectedTimestamp = DateUtils.formatISO8601(fixedDateTime);

    CurrencyPairRegisteredEvent currencyPairRegisteredEvent
        = new CurrencyPairRegisteredEvent(exchangeId, currencyPair);

    // Act
    eventBus.publish(GenericDomainEventMessage.asEventMessage(currencyPairRegisteredEvent));

    // Assert
    List<QuoteReadModel> quoteReadModels = readService.fetchQuotes(expectedExchangeId);
    assertThat(quoteReadModels).isNotNull();
    assertThat(quoteReadModels).isNotEmpty();
    assertThat(quoteReadModels.size()).isEqualTo(expectedSize);
    QuoteReadModel quoteReadModel = quoteReadModels.get(0);
    assertQuoteReadModelFieldsMatch(quoteReadModel, expectedExchangeId, expectedTickerSymbol, expectedBid, expectedAsk, expectedTimestamp);
  }

  @Test
  public void currencyPairRegistered_TwoEvents() {
    // Arrange
    ExchangeId expectedExchangeId = ExchangeIdFaker.createValid();
    CurrencyPair currencyPair1 = CurrencyPairFaker.createValid();
    CurrencyPairRegisteredEvent event1 = new CurrencyPairRegisteredEvent(expectedExchangeId, currencyPair1);
    CurrencyPair currencyPair2 = CurrencyPairFaker.createValid();
    CurrencyPairRegisteredEvent event2 = new CurrencyPairRegisteredEvent(expectedExchangeId, currencyPair2);
    int expectedSize = 2;

    // Act
    eventBus.publish(GenericDomainEventMessage.asEventMessage(event1));
    eventBus.publish(GenericDomainEventMessage.asEventMessage(event2));

    // Assert
    List<QuoteReadModel> quoteReadModels = readService.fetchQuotes(expectedExchangeId.getCode());
    assertThat(quoteReadModels).isNotNull();
    assertThat(quoteReadModels).isNotEmpty();
    assertThat(quoteReadModels.size()).isEqualTo(expectedSize);
  }

  private void assertQuoteReadModelFieldsMatch(
      QuoteReadModel quoteReadModel,
      String expectedExchangeId, String expectedTickerSymbol,
      String expectedBid, String expectedAsk, String expectedTimestamp) {
    assertThat(quoteReadModel.getExchangeId()).isEqualTo(expectedExchangeId);
    assertThat(quoteReadModel.getTicker()).isEqualTo(expectedTickerSymbol);
    assertThat(quoteReadModel.getBid()).isEqualTo(expectedBid);
    assertThat(quoteReadModel.getAsk()).isEqualTo(expectedAsk);
    assertThat(quoteReadModel.getTimestamp()).isEqualTo(expectedTimestamp);
  }
}
