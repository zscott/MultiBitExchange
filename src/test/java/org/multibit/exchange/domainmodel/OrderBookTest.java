package org.multibit.exchange.domainmodel;


import com.google.common.base.Optional;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;


import static org.fest.assertions.api.Assertions.assertThat;

public class OrderBookTest {

  OrderBook orderBook;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void setUp() {
    orderBook = new OrderBook();
  }

  @Test
  public void testTrade_OneMarketBidOrder() {
    // Arrange
    OrderAmount amount = new OrderAmount("10");

    // Act
    orderBook.add(new MarketBidOrder(SecurityOrderId.next(), amount));

    // Assert

  }

  @Test
  public void testTrade_OneMarketBidOrder_OneMarketAskOrder() {
    // Arrange
    OrderAmount amount = new OrderAmount("10");
    MarketBidOrder marketBidOrder = new MarketBidOrder(SecurityOrderId.next(), amount);
    MarketAskOrder marketAskOrder = new MarketAskOrder(SecurityOrderId.next(), amount);

    orderBook.add(marketBidOrder);
    Trade expectedTrade = new Trade(marketBidOrder, marketAskOrder);

    // Act
    Optional<Trade> tradeOptional = orderBook.add(marketAskOrder);

    // Assert
    assertThat(tradeOptional).isEqualTo(Optional.of(expectedTrade));
  }

}
