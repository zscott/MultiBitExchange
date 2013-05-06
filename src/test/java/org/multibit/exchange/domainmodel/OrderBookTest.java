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
    orderBook.add(new MarketBidOrder(SecurityOrderId.next(), quantity, nowUtc()));

    // Assert

  }

  @Test
  public void testAdd_OneMarketBidOrder_OneMarketAskOrder() throws DuplicateOrderException {
    // Arrange
    ItemQuantity quantity = new ItemQuantity("10");
    MarketBidOrder marketBidOrder = new MarketBidOrder(SecurityOrderId.next(), quantity, nowUtc());
    MarketAskOrder marketAskOrder = new MarketAskOrder(SecurityOrderId.next(), quantity, nowUtc());
    Trade expectedTrade = new Trade(marketBidOrder, marketAskOrder, quantity, new ItemPrice("0"));

    orderBook.add(marketBidOrder);

    // Act
    Optional<Trade> tradeOptional = orderBook.add(marketAskOrder);

    // Assert
    assertThat(tradeOptional).isEqualTo(Optional.of(expectedTrade));
  }

  @Test
  public void testGetLowestAsk_TwoAsks_OneOlder() throws DuplicateOrderException {
    // Arrange
    SecurityOrderComparator comparator = new SecurityOrderComparator();
    ItemQuantity quantity = new ItemQuantity("10");

    DateTime createdTime1 = DateUtils.thenUtc(2000, 1, 2, 1, 0, 0);
    MarketAskOrder olderOrder = new MarketAskOrder(SecurityOrderId.next(), quantity, createdTime1);

    DateTime oneSecondAfterCreatedTime1 = DateUtils.thenUtc(2000, 1, 2, 1, 0, 1);
    MarketAskOrder newerOrder = new MarketAskOrder(SecurityOrderId.next(), quantity, oneSecondAfterCreatedTime1);

    orderBook.add(olderOrder);
    orderBook.add(newerOrder);

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

    orderBook.add(newerOrder);
    orderBook.add(olderOrder);

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

    orderBook.add(olderOrder);
    orderBook.add(newerOrder);

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

    orderBook.add(newerOrder);
    orderBook.add(olderOrder);

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

    orderBook.add(marketBidOrder);

    // Act
    Optional<Trade> tradeOptional = orderBook.add(marketAskOrder1);

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

    orderBook.add(marketBidOrder);
    orderBook.add(marketAskOrder1);

    // Act
    Optional<Trade> tradeOptional = orderBook.add(marketAskOrder2);

    // Assert
    assertThat(tradeOptional).isEqualTo(Optional.of(expectedTrade));
  }

  @Test
  public void testAdd_ComplexSeriesOfMarketOrders() throws DuplicateOrderException {
    // Arrange
    MarketSecurityOrder bid1 = new MarketBidOrder(new SecurityOrderId("b1"), new ItemQuantity("10"), nowUtc());
    MarketSecurityOrder ask1 = new MarketAskOrder(new SecurityOrderId("a1"), new ItemQuantity("5"), nowUtc());
    MarketSecurityOrder bid2 = new MarketBidOrder(new SecurityOrderId("b2"), new ItemQuantity("6"), nowUtc());
    MarketSecurityOrder bid3 = new MarketBidOrder(new SecurityOrderId("b3"), new ItemQuantity("6"), nowUtc());
    MarketSecurityOrder ask2 = new MarketAskOrder(new SecurityOrderId("a2"), new ItemQuantity("0.5"), nowUtc());
    MarketSecurityOrder ask3 = new MarketAskOrder(new SecurityOrderId("a3"), new ItemQuantity("0.25"), nowUtc());
    MarketSecurityOrder ask4 = new MarketAskOrder(new SecurityOrderId("a4"), new ItemQuantity("0.1"), nowUtc());

    Trade expectedTrade = new Trade(bid1, ask4, new ItemQuantity("0.1"), new ItemPrice("0"));

    orderBook.add(bid1);
    orderBook.add(ask1);
    orderBook.add(bid2);
    orderBook.add(bid3);
    orderBook.add(ask2);
    orderBook.add(ask3);

    // Act
    Optional<Trade> tradeOptional = orderBook.add(ask4);

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

    orderBook.add(marketBidOrder);

    // Act
    orderBook.add(marketBidOrder);
  }

  @Test
  public void testAdd_DuplicateAskOrder() throws DuplicateOrderException {
    // Arrange
    ItemQuantity bidQuanity = new ItemQuantity("10");
    MarketAskOrder marketAskOrder = new MarketAskOrder(SecurityOrderId.next(), bidQuanity, nowUtc());
    thrown.expect(DuplicateOrderException.class);
    thrown.expectMessage("Duplicate order. id:" + marketAskOrder.getId());

    orderBook.add(marketAskOrder);

    // Act
    orderBook.add(marketAskOrder);
  }

}
