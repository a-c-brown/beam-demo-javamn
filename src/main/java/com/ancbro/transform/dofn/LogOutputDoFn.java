package com.ancbro.transform.dofn;

import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.values.KV;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogOutputDoFn extends DoFn<KV<String, Long>, String> {

  private static final Logger LOGGER = LoggerFactory.getLogger(LogOutputDoFn.class);

  @ProcessElement
  public void processElement(@Element KV<String, Long> element, OutputReceiver<String> out) {
    LOGGER.info("{} has {} votes", element.getKey(), element.getValue());
    out.output("");
  }
}
