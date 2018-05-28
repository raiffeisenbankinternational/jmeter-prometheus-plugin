package com.rbinternational.monitoring.jmeter;

import io.undertow.Handlers;
import io.undertow.Undertow;
import java.io.Serializable;
import org.apache.jmeter.engine.util.NoThreadClone;
import org.apache.jmeter.reporters.AbstractListenerElement;
import org.apache.jmeter.samplers.SampleEvent;
import org.apache.jmeter.samplers.SampleListener;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.testelement.TestStateListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PrometheusStatusExporter extends AbstractListenerElement
    implements SampleListener, Serializable, NoThreadClone, TestStateListener {

  /** */
  private static final long serialVersionUID = 1L;

  private static final Logger log = LoggerFactory.getLogger(PrometheusStatusExporter.class);

  private ItemHandler handler = new ItemHandler();

  private Undertow server;

  @Override
  public synchronized void sampleOccurred(SampleEvent se) {
    SampleResult res = se.getResult();
    ItemHandlerData data = handler.getData(res.getSampleLabel());
    data.getCount().incrementAndGet();
    data.getSumOfResponseTime().addAndGet(res.getTime());
    data.getSumTotalLatency().addAndGet(res.getLatency());
    data.getErrors().addAndGet(res.isSuccessful() ? 0 : 1);
    data.getThreads().set(res.getAllThreads());
  }

  @Override
  public void sampleStarted(SampleEvent se) {}

  @Override
  public void sampleStopped(SampleEvent se) {}

  @Override
  public void testStarted() {
    log.info("This started");

    if (server == null) {
      server =
          Undertow.builder()
              .addHttpListener(8080, "0.0.0.0")
              .setHandler(Handlers.pathTemplate().add("/metrics", handler))
              .build();
    }
    server.start();
  }

  @Override
  public void testStarted(String string) {
    testStarted();
  }

  @Override
  public void testEnded() {
    log.info("This ended");
    server.stop();
  }

  @Override
  public void testEnded(String string) {
    testEnded();
  }
}
