package org.multibit.exchange.infrastructure.adaptor.api.resources;

import org.junit.Test;
import org.multibit.exchange.domainmodel.Currency;
import org.multibit.exchange.domainmodel.Ticker;
import org.multibit.exchange.domainmodel.TradeableItem;
import org.multibit.exchange.infrastructure.adaptor.api.readmodel.ReadService;
import org.multibit.exchange.infrastructure.adaptor.api.readmodel.SecurityListReadModel;
import org.multibit.exchange.service.MarketService;
import org.multibit.exchange.testing.CurrencyFaker;
import org.multibit.exchange.testing.SecurityDescriptorFaker;
import org.multibit.exchange.testing.TickerFaker;
import org.multibit.exchange.testing.TradeableItemFaker;
import org.multibit.exchange.testing.web.BaseResourceTest;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class SecuritiesResourceFunctionalTest extends BaseResourceTest {

  private final MarketService marketService = mock(MarketService.class);
  private ReadService readService = mock(ReadService.class);
  private final SecuritiesResource securitiesResource = new SecuritiesResource(marketService, readService);

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
    securitiesResource.addSecurity(securityDescriptor);

    // Assert
    verify(marketService, times(1)).createSecurity(ticker, tradeableItem, currency);
  }

  @Test
  public void testGetSecurities() {
    // Arrange
    final int expectedMarketCount = 0;

    // Act
    SecurityListReadModel markets = securitiesResource.getSecurities();

    // Assert
    assertThat(markets.getSecurities()).isNotNull();
    assertThat(markets.getSecurities().size()).isEqualTo(expectedMarketCount);
  }
}
