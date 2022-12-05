package com.ancbro.io;

import com.ancbro.builder.InputGenerator;
import org.junit.Test;

import java.io.IOException;
import java.time.ZonedDateTime;

public class FileEventSourceTest {

  private static final String EVENT_TIME_MIN = "2020-11-01T00:00:00Z";
  private static final String EVENT_TIME_MAX = "2020-11-01T23:59:59Z";

  private final InputGenerator inputGenerator =
      new InputGenerator.Builder()
          .createForEventSource(new FileEventSource("src/test/resources/batch-votes.txt"))
          .withEventTimeMin(ZonedDateTime.parse(EVENT_TIME_MIN))
          .andEventTimeMax(ZonedDateTime.parse(EVENT_TIME_MAX))
          .withNumberOfEventsToGenerate(1000)
          .build();

  @Test
  public void generateGivenStringShouldWriteToFile() throws IOException {
    inputGenerator.generateEvents();
  }
}
