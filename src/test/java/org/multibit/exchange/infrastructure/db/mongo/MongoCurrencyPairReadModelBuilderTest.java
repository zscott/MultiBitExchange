package org.multibit.exchange.infrastructure.db.mongo;

import org.axonframework.domain.GenericDomainEventMessage;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.SimpleEventBus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.multibit.exchange.domain.command.ExchangeId;
import org.multibit.exchange.domain.event.CurrencyPairRegisteredEvent;
import org.multibit.exchange.domain.model.CurrencyPair;
import org.multibit.exchange.infrastructure.adaptor.persistence.mongo.MongoCurrencyPairReadModelBuilder;
import org.multibit.exchange.infrastructure.adaptor.persistence.mongo.MongoQueryProcessor;
import org.multibit.exchange.infrastructure.adaptor.web.restapi.readmodel.CurrencyPairReadModel;
import org.multibit.exchange.service.QueryProcessor;
import org.multibit.exchange.testing.CurrencyPairFaker;
import org.multibit.exchange.testing.ExchangeIdFaker;

import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;

public class MongoCurrencyPairReadModelBuilderTest extends BaseMongoDbTest {

  private MongoCurrencyPairReadModelBuilder readModelBuilder;
  private QueryProcessor readService;
  private EventBus eventBus = new SimpleEventBus();

  @Before
  public void setUp() {
    readModelBuilder = new MongoCurrencyPairReadModelBuilder(db, eventBus);
    readService = new MongoQueryProcessor(db);
  }

  @After
  public void tearDown() {
    dropAllCollections(db);
  }

  @Test
  public void currencyPairRegistered_OneEvent() {
    // Arrange
    ExchangeId exchangeId = ExchangeIdFaker.createValid();
    CurrencyPair currencyPair = CurrencyPairFaker.createValid();

    int expectedSize = 1;
    String expectedExchangeId = exchangeId.getCode();
    String expectedTickerSymbol = currencyPair.getTicker().getSymbol();
    String expectedBaseCurrency = currencyPair.getBaseCurrency().getSymbol();
    String expectedCounterCurrency = currencyPair.getCounterCurrency().getSymbol();
    CurrencyPairRegisteredEvent event = new CurrencyPairRegisteredEvent(exchangeId, currencyPair);

    // Act
    eventBus.publish(GenericDomainEventMessage.asEventMessage(event));

    // Assert
    List<CurrencyPairReadModel> currencyPairReadModels = readService.fetchSecurities(exchangeId.getCode());
    assertThat(currencyPairReadModels).isNotNull();
    assertThat(currencyPairReadModels).isNotEmpty();
    assertThat(currencyPairReadModels.size()).isEqualTo(expectedSize);
    CurrencyPairReadModel currencyPairReadModel = currencyPairReadModels.get(0);
    assertCurrencyPairReadModelFieldsEqual(currencyPairReadModel, expectedExchangeId, expectedTickerSymbol, expectedBaseCurrency, expectedCounterCurrency);
  }

  @Test
  public void currencyPairRegistered_TwoEvents() {
    // Arrange
    ExchangeId exchangeId = ExchangeIdFaker.createValid();
    CurrencyPair currencyPair1 = CurrencyPairFaker.createValid();
    CurrencyPairRegisteredEvent event1 = new CurrencyPairRegisteredEvent(exchangeId, currencyPair1);
    CurrencyPair currencyPair2 = CurrencyPairFaker.createValid();
    CurrencyPairRegisteredEvent event2 = new CurrencyPairRegisteredEvent(exchangeId, currencyPair2);
    int expectedSize = 2;

    // Act
    eventBus.publish(GenericDomainEventMessage.asEventMessage(event1));
    eventBus.publish(GenericDomainEventMessage.asEventMessage(event2));

    // Assert
    List<CurrencyPairReadModel> currencyPairReadModels = readService.fetchSecurities(exchangeId.getCode());
    assertThat(currencyPairReadModels).isNotNull();
    assertThat(currencyPairReadModels).isNotEmpty();
    assertThat(currencyPairReadModels.size()).isEqualTo(expectedSize);
  }

  private void assertCurrencyPairReadModelFieldsEqual(
      CurrencyPairReadModel currencyPairReadModel,
      String expectedExchangeId, String expectedTickerSymbol, String expectedBaseCurrency, String expectedCounterCurrency) {
    assertThat(currencyPairReadModel.getExchangeId()).isEqualTo(expectedExchangeId);
    assertThat(currencyPairReadModel.getTicker()).isEqualTo(expectedTickerSymbol);
    assertThat(currencyPairReadModel.getBaseCurrency()).isEqualTo(expectedBaseCurrency);
    assertThat(currencyPairReadModel.getCounterCurrency()).isEqualTo(expectedCounterCurrency);
  }
}
