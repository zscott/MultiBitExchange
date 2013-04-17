package com.blurtty.peregrine.infrastructure.disruptor;

import com.lmax.disruptor.BusySpinWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.EventTranslator;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SimpleDisruptorTest {


  private ExecutorService inputExec;
  private Disruptor<InputEvent> inputDisruptor;

  private ExecutorService outputExec;
  private Disruptor<OutputEvent> outputDisruptor;

  @Before
  public void setUp() {

    // Create Input Disruptor
    DisruptorFactory<InputEvent> inputDisruptorFactory = new DisruptorFactory<InputEvent>();

    inputExec = Executors.newFixedThreadPool(1);
    inputDisruptor = inputDisruptorFactory.create(inputExec, InputEvent.EVENT_FACTORY);


    // Create Output Disruptor
    DisruptorFactory<OutputEvent> outputDisruptorFactory = new DisruptorFactory<OutputEvent>();

    outputExec = Executors.newFixedThreadPool(1);
    outputDisruptor = outputDisruptorFactory.create(outputExec, OutputEvent.EVENT_FACTORY);


    // Wire Disruptors
    inputDisruptor.handleEventsWith(new BusinessLogicHandler(outputDisruptor));
    outputDisruptor.handleEventsWith(new CounterEventHandler());

    inputDisruptor.start();
    outputDisruptor.start();
  }

  @After
  public void tearDown() {
    inputDisruptor.shutdown();
    inputExec.shutdown();
    outputDisruptor.shutdown();
    outputExec.shutdown();
  }

  @Test
  public void testDisruptor() {
    long count = 100000;
    long start = System.currentTimeMillis();
    for (long i = 0; i < count; i++) {
      //final String uuid = UUID.randomUUID().toString();
      final String msg = "count:" + i;
      inputDisruptor.publishEvent(new EventTranslator<InputEvent>() {
        @Override
        public void translateTo(InputEvent event, long sequence) {
          event.setValue(msg);
        }
      });
    }
    long end = System.currentTimeMillis();
    System.out.println("Disruptor published " + count + " events in " + (end - start) + " milliseconds");
  }
}

class DisruptorFactory<T> {

  public static final int RING_BUFFER_SIZE = 1024 * 4;

  public Disruptor<T> create(ExecutorService exec, EventFactory<T> eventFactory) {

    return new Disruptor<T>(
        eventFactory,
        RING_BUFFER_SIZE,
        exec,
        ProducerType.SINGLE,
        new BusySpinWaitStrategy());
  }

}

class CounterEventHandler implements EventHandler<OutputEvent> {

  private long counter = 0;

  @Override
  public void onEvent(OutputEvent event, long sequence, boolean endOfBatch) throws Exception {
    counter++;
  }
}

class BusinessLogicHandler implements EventHandler<InputEvent> {

  private final Disruptor<OutputEvent> outputDisruptor;

  public BusinessLogicHandler(Disruptor<OutputEvent> outputDisruptor) {
    this.outputDisruptor = outputDisruptor;
  }

  @Override
  public void onEvent(InputEvent event, long sequence, boolean endOfBatch) throws Exception {
    businessLogicMethod(event);
  }

  private void businessLogicMethod(InputEvent event) {
    doStuff();
    publishResult("Got: " + event);
  }

  private void doStuff() {
    // do some business logic - no persistence please!
  }

  private void publishResult(final String message) {
    outputDisruptor.publishEvent(new EventTranslator<OutputEvent>() {
      @Override
      public void translateTo(OutputEvent event, long sequence) {
        event.setValue(message);
      }
    });
  }
}

class InputEvent {

  public static final EventFactory<InputEvent> EVENT_FACTORY = new EventFactory<InputEvent>() {
    public InputEvent newInstance() {
      return new InputEvent();
    }
  };

  private String value;

  public void setValue(String value) {
    this.value = value;
  }

  public String getValue() {
    return this.value;
  }

  @Override
  public String toString() {
    return "InputEvent{" +
        "value='" + value + '\'' +
        '}';
  }
}

class OutputEvent {

  public static final EventFactory<OutputEvent> EVENT_FACTORY = new EventFactory<OutputEvent>() {
    public OutputEvent newInstance() {
      return new OutputEvent();
    }
  };

  private String value;

  public void setValue(String value) {
    this.value = value;
  }

  public String getValue() {
    return this.value;
  }

  @Override
  public String toString() {
    return "OutputEvent{" +
        "value='" + value + '\'' +
        '}';
  }
}
