package com.rbinternational.monitoring.jmeter;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import javax.ws.rs.core.MediaType;
import org.apache.jmeter.threads.JMeterContextService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class ItemHandler implements HttpHandler {

  private static final Logger log = LoggerFactory.getLogger(ItemHandler.class);

  private ConcurrentHashMap<String, ItemHandlerData> handlers = new ConcurrentHashMap<>();

  @Override
  public void handleRequest(HttpServerExchange exchange) throws Exception {
    log.info("Current state: " + createResponse());

    exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, MediaType.TEXT_PLAIN);
    exchange.getResponseSender().send(createResponse());
  }

  private String createResponse() {
    return handlers.values().stream().map(this::createmMetrics).collect(Collectors.joining("\n"));
  }

  private String createmMetrics(ItemHandlerData data) {
    StringBuilder builder = new StringBuilder();

    String metric = "availiability_monitoring{";
    String metric2 = ",metric=\"";
    builder.append(
        metric
            + "sampler=\""
            + data.getSampler()
            + "\""
            + metric2
            + "total_threads\"} "
            + JMeterContextService.getTotalThreads()
            + System.lineSeparator());
    builder.append(
        metric
            + "sampler=\""
            + data.getSampler()
            + "\""
            + metric2
            + "total_samples\"} "
            + data.getCount().get()
            + System.lineSeparator());
    builder.append(
        metric
            + "sampler=\""
            + data.getSampler()
            + "\""
            + metric2
            + "total_latency\"} "
            + data.getSumTotalLatency().get()
            + System.lineSeparator());
    builder.append(
        metric
            + "sampler=\""
            + data.getSampler()
            + "\""
            + metric2
            + "total_response_time\"} "
            + data.getSumOfResponseTime().get()
            + System.lineSeparator());
    builder.append(
        metric
            + "sampler=\""
            + data.getSampler()
            + "\""
            + metric2
            + "total_errors\"} "
            + data.getErrors().get());
    return builder.toString();
  }

  public ItemHandlerData getData(String sampler) {
    handlers.putIfAbsent(sampler, new ItemHandlerData(sampler));
    return handlers.get(sampler);
  }
}
