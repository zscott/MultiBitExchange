package org.multibit.exchange.domainmodel;

import com.google.common.collect.Lists;
import com.lmax.disruptor.collections.Histogram;
import java.util.List;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TradePerformanceTest {

  OrderBook orderBook;
  RandomMarketOrderProvider orderProvider = new RandomMarketOrderProvider();

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void setUp() {
    orderBook = new OrderBook();
  }

  @Test
  public void testPerformance() throws DuplicateOrderException {
    // Arrange
    int nTrials = 10;
    int nOrders = 1000;
    long minRate = Long.MAX_VALUE;
    long maxRate = Long.MIN_VALUE;


    Histogram orderRateHist = new Histogram(new long[]{
        5000, 10000, 15000, 20000, 25000,
        30000, 35000, 40000, 45000, 50000,
        55000, 60000, 65000, 70000, 75000,
        80000, 85000, 90000, 95000, 100000
    });

    // Act
    for (int i = 0; i < nTrials; i++) {


      List<SecurityOrder> cachedOrders = buildCachedOrders(nOrders);


      long startNano = System.nanoTime();
      for (SecurityOrder order : cachedOrders) {
        orderBook.addOrderAndExecuteTrade(order);
      }
      long endNano = System.nanoTime();


      long elapsed = (endNano - startNano);
      long rate = (long) ((double) nOrders / ((double) elapsed / 1000000000d));
      orderRateHist.addObservation(rate);
      if (minRate > rate)
        minRate = rate;
      if (maxRate < rate)
        maxRate = rate;
    }

    System.out.println("\nOrder Rate  Histogram  (# orders / second)");
    System.out.println(orderRateHist.toString());

    double benchmark = orderRateHist.getMean().doubleValue() / mathComputationRate();
    System.out.println("Benchmark = " + benchmark);
  }

  private List<SecurityOrder> buildCachedOrders(int nOrders) {
    List<SecurityOrder> cachedOrders = Lists.newArrayListWithExpectedSize(nOrders);
    for (int x = 0; x < nOrders; x++) {
      cachedOrders.add(orderProvider.get());
    }
    return cachedOrders;
  }

  private long mathComputationRate() {
    long val = 1107;
    long start = System.currentTimeMillis();
    for (long i = 0; i < 100000000l; i++) {
      val = val + 7;
      val = val * 17 * 5;
      val = val / 11;
      val = val + 27;
    }
    long end = System.currentTimeMillis();
    System.out.println("bogus value: " + val);
    return (end - start) * 88;
  }

}
