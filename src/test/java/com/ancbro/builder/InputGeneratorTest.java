package com.ancbro.builder;

import com.ancbro.io.FileEventSource;
import org.junit.Assert;
import org.junit.Test;

import java.time.ZonedDateTime;

public class InputGeneratorTest {

  private static final String EVENT_TIME_MIN = "2019-01-01T00:00:00Z";
  private static final String EVENT_TIME_MAX = "2019-01-01T11:59:59Z";

  @Test
  public void toStringInputGeneratorShouldNotBeNull() {
    InputGenerator inputGenerator =
        new InputGenerator.Builder()
            .createForEventSource(new FileEventSource("src/test/resources/batch-votes.txt"))
            .withEventTimeMin(ZonedDateTime.parse(EVENT_TIME_MIN))
            .andEventTimeMax(ZonedDateTime.parse(EVENT_TIME_MAX))
            .withNumberOfEventsToGenerate(250)
            .build();
    Assert.assertNotNull(inputGenerator);
  }
}
