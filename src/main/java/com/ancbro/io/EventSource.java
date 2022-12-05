package com.ancbro.io;

import com.ancbro.ElectionNightBatchPipeline;

import java.io.IOException;

/**
 * {@link EventSource specifies the type of source {@link ElectionNightBatchPipeline will read
 * from.}}
 */
public interface EventSource {
  void commitToEventSource(String line) throws IOException;
}
