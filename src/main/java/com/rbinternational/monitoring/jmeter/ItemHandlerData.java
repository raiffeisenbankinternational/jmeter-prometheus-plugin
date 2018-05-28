package com.rbinternational.monitoring.jmeter;

import java.util.concurrent.atomic.AtomicLong;

public class ItemHandlerData {
  private String sampler = "";
  private AtomicLong count = new AtomicLong();
  private AtomicLong threads = new AtomicLong();
  private AtomicLong sumOfResponseTime = new AtomicLong();
  private AtomicLong sumTotalLatency = new AtomicLong();
  private AtomicLong errors = new AtomicLong();

  public ItemHandlerData(String sampler) {
    this.sampler = sampler;
  }

  public String getSampler() {
    return sampler;
  }

  public AtomicLong getCount() {
    return count;
  }

  public AtomicLong getThreads() {
    return threads;
  }

  public AtomicLong getSumOfResponseTime() {
    return sumOfResponseTime;
  }

  public AtomicLong getSumTotalLatency() {
    return sumTotalLatency;
  }

  public AtomicLong getErrors() {
    return errors;
  }
}
