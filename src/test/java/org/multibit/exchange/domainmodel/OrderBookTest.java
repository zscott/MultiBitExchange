package org.multibit.exchange.domainmodel;


import com.google.common.base.Optional;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.multibit.common.DateUtils;


import static org.fest.assertions.api.Assertions.assertThat;
import static org.multibit.common.DateUtils.nowUtc;

public class OrderBookTest {

  OrderBook orderBook;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void setUp() {
    orderBook = new OrderBook();
  }

  @Test
  public void testAdd_OneMarketBidOrder() throws DuplicateOrderException {
    // Arrange
    ItemQuantity quantity = new ItemQuantity("10");

    // Act
    orderBook.addBidOrderAndMatchAsks(new MarketBidOrder(SecurityOrderId.next(), quantity, nowUtc()));

    // Assert

  }

  @Test
  public void testAdd_OneMarketBidOrder_OneMarketAskOrder() throws DuplicateOrderException {
    // Arrange
    ItemQuantity quantity = new ItemQuantity("10");
    MarketBidOrder marketBidOrder = new MarketBidOrder(SecurityOrderId.next(), quantity, nowUtc());
    MarketAskOrder marketAskOrder = new MarketAskOrder(SecurityOrderId.next(), quantity, nowUtc());
    Trade expectedTrade = new Trade(marketBidOrder, marketAskOrder, quantity, new ItemPrice("0"));

    orderBook.addBidOrderAndMatchAsks(marketBidOrder);

    // Act
    Optional<Trade> tradeOptional = orderBook.addAskOrderAndMatchBids(marketAskOrder);

    // Assert
    assertThat(tradeOptional).isEqualTo(Optional.of(expectedTrade));
  }

  @Test
  public void testGetLowestAsk_TwoAsks_OneOlder() throws DuplicateOrderException {
    // Arrange
    ItemQuantity quantity = new ItemQuantity("10");

    DateTime createdTime1 = DateUtils.thenUtc(2000, 1, 2, 1, 0, 0);
    MarketAskOrder olderOrder = new MarketAskOrder(SecurityOrderId.next(), quantity, createdTime1);

    DateTime oneSecondAfterCreatedTime1 = DateUtils.thenUtc(2000, 1, 2, 1, 0, 1);
    MarketAskOrder newerOrder = new MarketAskOrder(SecurityOrderId.next(), quantity, oneSecondAfterCreatedTime1);

    orderBook.addAskOrderAndMatchBids(olderOrder);
    orderBook.addAskOrderAndMatchBids(newerOrder);

    // Act
    SecurityOrder lowestAsk = orderBook.getLowestAsk();

    // Assert
    assertThat(lowestAsk).isEqualTo(olderOrder);
  }

  @Test
  public void testGetLowestAsk_TwoAsks_OneOlder_AddedInReverse() throws DuplicateOrderException {
    // Arrange
    ItemQuantity quantity = new ItemQuantity("10");

    DateTime createdTime1 = DateUtils.thenUtc(2000, 1, 2, 1, 0, 0);
    MarketAskOrder olderOrder = new MarketAskOrder(SecurityOrderId.next(), quantity, createdTime1);

    DateTime oneSecondAfterCreatedTime1 = DateUtils.thenUtc(2000, 1, 2, 1, 0, 1);
    MarketAskOrder newerOrder = new MarketAskOrder(SecurityOrderId.next(), quantity, oneSecondAfterCreatedTime1);

    orderBook.addAskOrderAndMatchBids(newerOrder);
    orderBook.addAskOrderAndMatchBids(olderOrder);

    // Act
    SecurityOrder lowestAsk = orderBook.getLowestAsk();

    // Assert
    assertThat(lowestAsk).isEqualTo(olderOrder);
  }

  @Test
  public void testGetHighestBid_TwoBids_OneOlder() throws DuplicateOrderException {
    // Arrange
    ItemQuantity quantity = new ItemQuantity("10");

    DateTime createdTime1 = DateUtils.thenUtc(2000, 1, 2, 1, 0, 0);
    MarketBidOrder olderOrder = new MarketBidOrder(SecurityOrderId.next(), quantity, createdTime1);

    DateTime oneSecondAfterCreatedTime1 = DateUtils.thenUtc(2000, 1, 2, 1, 0, 1);
    MarketBidOrder newerOrder = new MarketBidOrder(SecurityOrderId.next(), quantity, oneSecondAfterCreatedTime1);

    orderBook.addBidOrderAndMatchAsks(olderOrder);
    orderBook.addBidOrderAndMatchAsks(newerOrder);

    // Act
    SecurityOrder highestBid = orderBook.getHighestBid();

    // Assert
    assertThat(highestBid).isEqualTo(olderOrder);
  }

  @Test
  public void testGetHighestBid_TwoBids_OneOlder_AddedInReverse() throws DuplicateOrderException {
    // Arrange
    ItemQuantity quantity = new ItemQuantity("10");

    DateTime createdTime1 = DateUtils.thenUtc(2000, 1, 2, 1, 0, 0);
    MarketBidOrder olderOrder = new MarketBidOrder(SecurityOrderId.next(), quantity, createdTime1);

    DateTime oneSecondAfterCreatedTime1 = DateUtils.thenUtc(2000, 1, 2, 1, 0, 1);
    MarketBidOrder newerOrder = new MarketBidOrder(SecurityOrderId.next(), quantity, oneSecondAfterCreatedTime1);

    orderBook.addBidOrderAndMatchAsks(newerOrder);
    orderBook.addBidOrderAndMatchAsks(olderOrder);

    // Act
    SecurityOrder highestBid = orderBook.getHighestBid();

    // Assert
    assertThat(highestBid).isEqualTo(olderOrder);
  }

  @Test
  public void testAdd_OneMarketBidOrder_1SmallerMarketAsk() throws DuplicateOrderException {
    // Arrange
    ItemQuantity bidQuanity = new ItemQuantity("10");
    MarketBidOrder marketBidOrder = new MarketBidOrder(SecurityOrderId.next(), bidQuanity, nowUtc());

    ItemQuantity askQuantity = new ItemQuantity("5");
    MarketAskOrder marketAskOrder1 = new MarketAskOrder(SecurityOrderId.next(), askQuantity, nowUtc());
    Trade expectedTrade = new Trade(marketBidOrder, marketAskOrder1, askQuantity, new ItemPrice("0"));

    orderBook.addBidOrderAndMatchAsks(marketBidOrder);

    // Act
    Optional<Trade> tradeOptional = orderBook.addAskOrderAndMatchBids(marketAskOrder1);

    // Assert
    assertThat(tradeOptional).isEqualTo(Optional.of(expectedTrade));
  }

  @Test
  public void testAdd_OneMarketBidOrder_2SmallerMarketAsks() throws DuplicateOrderException {
    // Arrange
    ItemQuantity bidQuanity = new ItemQuantity("10");
    MarketBidOrder marketBidOrder = new MarketBidOrder(SecurityOrderId.next(), bidQuanity, nowUtc());

    ItemQuantity askQuantity = new ItemQuantity("1");
    MarketAskOrder marketAskOrder1 = new MarketAskOrder(SecurityOrderId.next(), askQuantity, nowUtc());
    MarketAskOrder marketAskOrder2 = new MarketAskOrder(SecurityOrderId.next(), askQuantity, nowUtc());
    Trade expectedTrade = new Trade(marketBidOrder, marketAskOrder2, askQuantity, new ItemPrice("0"));

    orderBook.addBidOrderAndMatchAsks(marketBidOrder);
    orderBook.addAskOrderAndMatchBids(marketAskOrder1);

    // Act
    Optional<Trade> tradeOptional = orderBook.addAskOrderAndMatchBids(marketAskOrder2);

    // Assert
    assertThat(tradeOptional).isEqualTo(Optional.of(expectedTrade));
  }

  @Test
  public void testAdd_ComplexSeriesOfMarketOrders1() throws DuplicateOrderException {
    // Arrange
    SecurityOrder bid1 = new MarketBidOrder(new SecurityOrderId("b1"), new ItemQuantity("10"), nowUtc());
    SecurityOrder ask1 = new MarketAskOrder(new SecurityOrderId("a1"), new ItemQuantity("5"), nowUtc());
    SecurityOrder bid2 = new MarketBidOrder(new SecurityOrderId("b2"), new ItemQuantity("6"), nowUtc());
    SecurityOrder bid3 = new MarketBidOrder(new SecurityOrderId("b3"), new ItemQuantity("6"), nowUtc());
    SecurityOrder ask2 = new MarketAskOrder(new SecurityOrderId("a2"), new ItemQuantity("0.5"), nowUtc());
    SecurityOrder ask3 = new MarketAskOrder(new SecurityOrderId("a3"), new ItemQuantity("0.25"), nowUtc());
    SecurityOrder ask4 = new MarketAskOrder(new SecurityOrderId("a4"), new ItemQuantity("0.1"), nowUtc());

    Trade expectedTrade = new Trade(bid1, ask4, new ItemQuantity("0.1"), new ItemPrice("0"));

    orderBook.addOrderAndExecuteTrade(bid1);
    orderBook.addOrderAndExecuteTrade(ask1);
    orderBook.addOrderAndExecuteTrade(bid2);
    orderBook.addOrderAndExecuteTrade(bid3);
    orderBook.addOrderAndExecuteTrade(ask2);
    orderBook.addOrderAndExecuteTrade(ask3);

    // Act
    Optional<Trade> tradeOptional = orderBook.addOrderAndExecuteTrade(ask4);

    // Assert
    assertThat(tradeOptional).isEqualTo(Optional.of(expectedTrade));
  }

  @Test
  public void testAdd_EscalatingSeriesOfMarketOrders1() throws DuplicateOrderException {
    // Arrange
    DateTime startTime = nowUtc();
    SecurityOrder bid1 = new MarketBidOrder(new SecurityOrderId("bid1"), new ItemQuantity("1"), startTime);
    SecurityOrder ask1 = new MarketAskOrder(new SecurityOrderId("ask1"), new ItemQuantity("2"), startTime.plusMillis(1));
    //SecurityOrder bid2 = new MarketBidOrder(new SecurityOrderId("bid2"), new ItemQuantity("3"), startTime.plusMillis(2));
    //SecurityOrder ask2 = new MarketAskOrder(new SecurityOrderId("ask2"), new ItemQuantity("4"), startTime.plusMillis(3));
    //SecurityOrder bid3 = new MarketBidOrder(new SecurityOrderId("bid3"), new ItemQuantity("5"), startTime.plusMillis(4));
    //SecurityOrder ask3 = new MarketAskOrder(new SecurityOrderId("ask3"), new ItemQuantity("6"), startTime.plusMillis(5));

    Trade expectedTrade = new Trade(bid1, ask1, new ItemQuantity("1"), new ItemPrice("0"));

    orderBook.addOrderAndExecuteTrade(bid1);

    // Act
    Optional<Trade> tradeOptional = orderBook.addOrderAndExecuteTrade(ask1);

    // Assert
    assertThat(tradeOptional).isEqualTo(Optional.of(expectedTrade));
  }

  @Test
  public void testAdd_EscalatingSeriesOfMarketOrders2() throws DuplicateOrderException {
    // Arrange
    DateTime startTime = nowUtc();
    SecurityOrder bid1 = new MarketBidOrder(new SecurityOrderId("bid1"), new ItemQuantity("1"), startTime);
    SecurityOrder ask1 = new MarketAskOrder(new SecurityOrderId("ask1"), new ItemQuantity("2"), startTime.plusMillis(1));
    SecurityOrder bid2 = new MarketBidOrder(new SecurityOrderId("bid2"), new ItemQuantity("3"), startTime.plusMillis(2));
    SecurityOrder ask2 = new MarketAskOrder(new SecurityOrderId("ask2"), new ItemQuantity("4"), startTime.plusMillis(3));

    Trade expectedTrade = new Trade(bid2, ask2, new ItemQuantity("2"), new ItemPrice("0"));

    orderBook.addOrderAndExecuteTrade(bid1);
    orderBook.addOrderAndExecuteTrade(ask1);
    orderBook.addOrderAndExecuteTrade(bid2);

    // Act
    Optional<Trade> tradeOptional = orderBook.addOrderAndExecuteTrade(ask2);

    // Assert
    assertThat(tradeOptional).isEqualTo(Optional.of(expectedTrade));
  }

  @Test
  public void testAdd_DuplicateBidOrder() throws DuplicateOrderException {
    // Arrange
    ItemQuantity bidQuanity = new ItemQuantity("10");
    MarketBidOrder marketBidOrder = new MarketBidOrder(SecurityOrderId.next(), bidQuanity, nowUtc());
    thrown.expect(DuplicateOrderException.class);
    thrown.expectMessage("Duplicate order. id:" + marketBidOrder.getId());

    orderBook.addBidOrderAndMatchAsks(marketBidOrder);

    // Act
    orderBook.addBidOrderAndMatchAsks(marketBidOrder);
  }

  @Test
  public void testAdd_DuplicateAskOrder() throws DuplicateOrderException {
    // Arrange
    ItemQuantity bidQuanity = new ItemQuantity("10");
    MarketAskOrder marketAskOrder = new MarketAskOrder(SecurityOrderId.next(), bidQuanity, nowUtc());
    thrown.expect(DuplicateOrderException.class);
    thrown.expectMessage("Duplicate order. id:" + marketAskOrder.getId());

    orderBook.addAskOrderAndMatchBids(marketAskOrder);

    // Act
    orderBook.addAskOrderAndMatchBids(marketAskOrder);
  }

}
