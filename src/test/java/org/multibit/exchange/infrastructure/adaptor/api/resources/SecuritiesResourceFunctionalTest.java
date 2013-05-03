package org.multibit.exchange.infrastructure.adaptor.api.resources;

import org.junit.Test;
import org.multibit.exchange.domainmodel.Currency;
import org.multibit.exchange.domainmodel.ExchangeId;
import org.multibit.exchange.domainmodel.Ticker;
import org.multibit.exchange.domainmodel.TradeableItem;
import org.multibit.exchange.infrastructure.adaptor.api.readmodel.ReadService;
import org.multibit.exchange.infrastructure.adaptor.api.readmodel.SecurityListReadModel;
import org.multibit.exchange.infrastructure.adaptor.api.readmodel.SecurityReadModel;
import org.multibit.exchange.service.ExchangeService;
import org.multibit.exchange.testing.CurrencyFaker;
import org.multibit.exchange.testing.TickerFaker;
import org.multibit.exchange.testing.TradeableItemFaker;
import org.multibit.exchange.testing.web.BaseResourceTest;

import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class SecuritiesResourceFunctionalTest extends BaseResourceTest {

  public static final String EXCHANGE_NAME = "test-exchange";
  private ExchangeId exchangeId = new ExchangeId(EXCHANGE_NAME);

  private final ExchangeService exchangeService = mock(ExchangeService.class);
  private ReadService readService = mock(ReadService.class);
  private final SecuritiesResource securitiesResource = new SecuritiesResource(exchangeService, readService);


  @Test
  public void testAddSecurity() {
    // Arrange
    final Ticker ticker = TickerFaker.createValid();
    final TradeableItem tradeableItem = TradeableItemFaker.createValid();
    final Currency currency = CurrencyFaker.createValid();
    final SecurityDescriptor securityDescriptor = new SecurityDescriptor(
      ticker.getSymbol(),
      tradeableItem.getSymbol(),
      currency.getSymbol());

    // Act
    securitiesResource.addSecurity(EXCHANGE_NAME, securityDescriptor);

    // Assert
    verify(exchangeService, times(1)).createSecurity(exchangeId, ticker, tradeableItem, currency);
  }

  @Test
  public void testGetSecurities() {
    // Arrange
    final int expectedCount = 0;

    // Act
    SecurityListReadModel securityListReadModel = securitiesResource.getSecurities(EXCHANGE_NAME);

    // Assert
    assertThat(securityListReadModel).isNotNull();
    List<SecurityReadModel> securities = securityListReadModel.getSecurities();
    assertThat(securities).isNotNull();
    assertThat(securities.size()).isEqualTo(expectedCount);
  }
}
