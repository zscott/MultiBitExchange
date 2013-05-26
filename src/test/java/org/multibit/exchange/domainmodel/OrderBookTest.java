package org.multibit.exchange.domainmodel;


import com.google.common.base.Optional;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.multibit.common.DateUtils;
import org.multibit.exchange.testing.CurrencyPairFaker;


import static org.fest.assertions.api.Assertions.assertThat;
import static org.multibit.common.DateUtils.nowUtc;

public class OrderBookTest {

  OrderBook orderBook;
  CurrencyPair currencyPair = CurrencyPairFaker.createValid();

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void setUp() {
    orderBook = new OrderBook(currencyPair);
  }

  @Test
  public void testAdd_OneMarketBidOrder() throws DuplicateOrderException {
    // Arrange
    ItemQuantity quantity = new ItemQuantity("10");

    // Act
    final SecurityOrderId id = SecurityOrderId.next();
    final ItemQuantity quantity1 = quantity;
    final DateTime createdTime = nowUtc();
    orderBook.addBidOrderAndMatchAsks(new BuyOrder(id, OrderType.marketOrder(), quantity1, createdTime));

    // Assert

  }

  @Test
  public void testAdd_OneMarketBidOrder_OneMarketAskOrder() throws DuplicateOrderException {
    // Arrange
    ItemQuantity quantity = new ItemQuantity("10");
    final SecurityOrderId id = SecurityOrderId.next();
    final ItemQuantity quantity1 = quantity;
    final DateTime createdTime = nowUtc();
    BuyOrder marketBidOrder = new BuyOrder(id, OrderType.marketOrder(), quantity1, createdTime);
    final SecurityOrderId id1 = SecurityOrderId.next();
    final ItemQuantity quantity2 = quantity;
    final DateTime createdTime1 = nowUtc();
    SellOrder marketAskOrder = new SellOrder(id1, OrderType.marketOrder(), quantity2, createdTime1);
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
    final SecurityOrderId id = SecurityOrderId.next();
    final ItemQuantity quantity1 = quantity;
    final DateTime createdTime = createdTime1;
    SellOrder olderOrder = new SellOrder(id, OrderType.marketOrder(), quantity1, createdTime);

    DateTime oneSecondAfterCreatedTime1 = DateUtils.thenUtc(2000, 1, 2, 1, 0, 1);
    final SecurityOrderId id1 = SecurityOrderId.next();
    final ItemQuantity quantity2 = quantity;
    final DateTime createdTime2 = oneSecondAfterCreatedTime1;
    SellOrder newerOrder = new SellOrder(id1, OrderType.marketOrder(), quantity2, createdTime2);

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
    final SecurityOrderId id = SecurityOrderId.next();
    final ItemQuantity quantity1 = quantity;
    final DateTime createdTime = createdTime1;
    SellOrder olderOrder = new SellOrder(id, OrderType.marketOrder(), quantity1, createdTime);

    DateTime oneSecondAfterCreatedTime1 = DateUtils.thenUtc(2000, 1, 2, 1, 0, 1);
    final SecurityOrderId id1 = SecurityOrderId.next();
    final ItemQuantity quantity2 = quantity;
    final DateTime createdTime2 = oneSecondAfterCreatedTime1;
    SellOrder newerOrder = new SellOrder(id1, OrderType.marketOrder(), quantity2, createdTime2);

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
    final SecurityOrderId id = SecurityOrderId.next();
    final ItemQuantity quantity1 = quantity;
    final DateTime createdTime = createdTime1;
    BuyOrder olderOrder = new BuyOrder(id, OrderType.marketOrder(), quantity1, createdTime);

    DateTime oneSecondAfterCreatedTime1 = DateUtils.thenUtc(2000, 1, 2, 1, 0, 1);
    final SecurityOrderId id1 = SecurityOrderId.next();
    final ItemQuantity quantity2 = quantity;
    final DateTime createdTime2 = oneSecondAfterCreatedTime1;
    BuyOrder newerOrder = new BuyOrder(id1, OrderType.marketOrder(), quantity2, createdTime2);

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
    final SecurityOrderId id = SecurityOrderId.next();
    final ItemQuantity quantity1 = quantity;
    final DateTime createdTime = createdTime1;
    BuyOrder olderOrder = new BuyOrder(id, OrderType.marketOrder(), quantity1, createdTime);

    DateTime oneSecondAfterCreatedTime1 = DateUtils.thenUtc(2000, 1, 2, 1, 0, 1);
    final SecurityOrderId id1 = SecurityOrderId.next();
    final ItemQuantity quantity2 = quantity;
    final DateTime createdTime2 = oneSecondAfterCreatedTime1;
    BuyOrder newerOrder = new BuyOrder(id1, OrderType.marketOrder(), quantity2, createdTime2);

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
    final SecurityOrderId id = SecurityOrderId.next();
    final ItemQuantity quantity = bidQuanity;
    final DateTime createdTime = nowUtc();
    BuyOrder marketBidOrder = new BuyOrder(id, OrderType.marketOrder(), quantity, createdTime);

    ItemQuantity askQuantity = new ItemQuantity("5");
    final SecurityOrderId id1 = SecurityOrderId.next();
    final ItemQuantity quantity1 = askQuantity;
    final DateTime createdTime1 = nowUtc();
    SellOrder marketAskOrder1 = new SellOrder(id1, OrderType.marketOrder(), quantity1, createdTime1);
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
    final SecurityOrderId id = SecurityOrderId.next();
    final ItemQuantity quantity = bidQuanity;
    final DateTime createdTime = nowUtc();
    BuyOrder marketBidOrder = new BuyOrder(id, OrderType.marketOrder(), quantity, createdTime);

    ItemQuantity askQuantity = new ItemQuantity("1");
    final SecurityOrderId id1 = SecurityOrderId.next();
    final ItemQuantity quantity1 = askQuantity;
    final DateTime createdTime1 = nowUtc();
    SellOrder marketAskOrder1 = new SellOrder(id1, OrderType.marketOrder(), quantity1, createdTime1);
    final SecurityOrderId id2 = SecurityOrderId.next();
    final ItemQuantity quantity2 = askQuantity;
    final DateTime createdTime2 = nowUtc();
    SellOrder marketAskOrder2 = new SellOrder(id2, OrderType.marketOrder(), quantity2, createdTime2);
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
    final SecurityOrderId id = new SecurityOrderId("b1");
    final ItemQuantity quantity = new ItemQuantity("10");
    final DateTime createdTime = nowUtc();
    SecurityOrder bid1 = new BuyOrder(id, OrderType.marketOrder(), quantity, createdTime);
    final SecurityOrderId id3 = new SecurityOrderId("a1");
    final ItemQuantity quantity3 = new ItemQuantity("5");
    final DateTime createdTime3 = nowUtc();
    SecurityOrder ask1 = new SellOrder(id3, OrderType.marketOrder(), quantity3, createdTime3);
    final SecurityOrderId id1 = new SecurityOrderId("b2");
    final ItemQuantity quantity1 = new ItemQuantity("6");
    final DateTime createdTime1 = nowUtc();
    SecurityOrder bid2 = new BuyOrder(id1, OrderType.marketOrder(), quantity1, createdTime1);
    final SecurityOrderId id2 = new SecurityOrderId("b3");
    final ItemQuantity quantity2 = new ItemQuantity("6");
    final DateTime createdTime2 = nowUtc();
    SecurityOrder bid3 = new BuyOrder(id2, OrderType.marketOrder(), quantity2, createdTime2);
    final SecurityOrderId id4 = new SecurityOrderId("a2");
    final ItemQuantity quantity4 = new ItemQuantity("0.5");
    final DateTime createdTime4 = nowUtc();
    SecurityOrder ask2 = new SellOrder(id4, OrderType.marketOrder(), quantity4, createdTime4);
    final SecurityOrderId id5 = new SecurityOrderId("a3");
    final ItemQuantity quantity5 = new ItemQuantity("0.25");
    final DateTime createdTime5 = nowUtc();
    SecurityOrder ask3 = new SellOrder(id5, OrderType.marketOrder(), quantity5, createdTime5);
    final SecurityOrderId id6 = new SecurityOrderId("a4");
    final ItemQuantity quantity6 = new ItemQuantity("0.1");
    final DateTime createdTime6 = nowUtc();
    SecurityOrder ask4 = new SellOrder(id6, OrderType.marketOrder(), quantity6, createdTime6);

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
    final SecurityOrderId id = new SecurityOrderId("bid1");
    final ItemQuantity quantity = new ItemQuantity("1");
    final DateTime createdTime = startTime;
    SecurityOrder bid1 = new BuyOrder(id, OrderType.marketOrder(), quantity, createdTime);
    final SecurityOrderId id1 = new SecurityOrderId("ask1");
    final ItemQuantity quantity1 = new ItemQuantity("2");
    final DateTime createdTime1 = startTime.plusMillis(1);
    SecurityOrder ask1 = new SellOrder(id1, OrderType.marketOrder(), quantity1, createdTime1);
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
    final SecurityOrderId id = new SecurityOrderId("bid1");
    final ItemQuantity quantity = new ItemQuantity("1");
    final DateTime createdTime = startTime;
    SecurityOrder bid1 = new BuyOrder(id, OrderType.marketOrder(), quantity, createdTime);
    final SecurityOrderId id2 = new SecurityOrderId("ask1");
    final ItemQuantity quantity2 = new ItemQuantity("2");
    final DateTime createdTime2 = startTime.plusMillis(1);
    SecurityOrder ask1 = new SellOrder(id2, OrderType.marketOrder(), quantity2, createdTime2);
    final SecurityOrderId id1 = new SecurityOrderId("bid2");
    final ItemQuantity quantity1 = new ItemQuantity("3");
    final DateTime createdTime1 = startTime.plusMillis(2);
    SecurityOrder bid2 = new BuyOrder(id1, OrderType.marketOrder(), quantity1, createdTime1);
    final SecurityOrderId id3 = new SecurityOrderId("ask2");
    final ItemQuantity quantity3 = new ItemQuantity("4");
    final DateTime createdTime3 = startTime.plusMillis(3);
    SecurityOrder ask2 = new SellOrder(id3, OrderType.marketOrder(), quantity3, createdTime3);

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
    final SecurityOrderId id = SecurityOrderId.next();
    final ItemQuantity quantity = bidQuanity;
    final DateTime createdTime = nowUtc();
    BuyOrder marketBidOrder = new BuyOrder(id, OrderType.marketOrder(), quantity, createdTime);
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
    final SecurityOrderId id = SecurityOrderId.next();
    final ItemQuantity quantity = bidQuanity;
    final DateTime createdTime = nowUtc();
    SellOrder marketAskOrder = new SellOrder(id, OrderType.marketOrder(), quantity, createdTime);
    thrown.expect(DuplicateOrderException.class);
    thrown.expectMessage("Duplicate order. id:" + marketAskOrder.getId());

    orderBook.addAskOrderAndMatchBids(marketAskOrder);

    // Act
    orderBook.addAskOrderAndMatchBids(marketAskOrder);
  }

}
