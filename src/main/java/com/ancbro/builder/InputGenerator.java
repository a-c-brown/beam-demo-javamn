package com.ancbro.builder;

import com.ancbro.domain.Ballot;
import com.ancbro.domain.Candidate;
import com.ancbro.domain.Voter;
import com.ancbro.io.EventSource;
import com.ancbro.serde.ObjectMapperSingleton;
import com.ancbro.util.Maths;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.IntStream;

public class InputGenerator {

  private static final Logger LOGGER = LoggerFactory.getLogger(InputGenerator.class);

  private static final String ISO_8601_TIMESTAMP_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";

  private EventSource eventSource;
  private ZonedDateTime eventTimeMin;
  private ZonedDateTime eventTimeMax;
  private int numberOfEventsToGenerate;

  private InputGenerator(
      EventSource eventSource,
      ZonedDateTime eventTimeMin,
      ZonedDateTime eventTimeMax,
      int numberOfEventsToGenerate) {
    this.eventSource = eventSource;
    this.eventTimeMin = eventTimeMin;
    this.eventTimeMax = eventTimeMax;
    this.numberOfEventsToGenerate = numberOfEventsToGenerate;
  }

  private String appendRandomEvents() {
    StringBuilder stringBuilder = new StringBuilder();
    IntStream.range(0, this.getNumberOfEventsToGenerate())
        .forEach(
            i -> {
              try {
                stringBuilder.append(generateRandomEvent());
              } catch (JsonProcessingException e) {
                LOGGER.error(e.getMessage());
              }
            });
    return stringBuilder.toString();
  }

  private String generateRandomEvent() throws JsonProcessingException {
    ZonedDateTime zonedDateTime =
        Maths.GET_RANDOM_ZONED_DATE_TIME_BETWEEN.apply(
            this.getEventTimeMin(), this.getEventTimeMax());
    DateTimeFormatter format = DateTimeFormatter.ofPattern(ISO_8601_TIMESTAMP_FORMAT);
    String eventTime = zonedDateTime.format(format);
    Voter voter = new Voter();
    Ballot ballot = new Ballot(voter.getVoterId().get(), eventTime, chooseCandidate());
    return ObjectMapperSingleton.INSTANCE.getObjectMapper().writeValueAsString(ballot) + "\n";
  }

  public void generateEvents() {
    try {
      this.getEventSource().commitToEventSource(appendRandomEvents());
    } catch (IOException e) {
      LOGGER.error(e.getMessage());
    }
  }

  private String chooseCandidate() {
    Candidate candidate;
    long choice = Maths.GET_RANDOM_NUMBER_IN_RANGE.applyAsLong(1, 3);
    if (choice == 1) {
      candidate = Candidate.CANDIDATE_A;
    } else {
      candidate = Candidate.CANDIDATE_B;
    }
    return candidate.toString();
  }

  public EventSource getEventSource() {
    return eventSource;
  }

  public void setEventSource(EventSource eventSource) {
    this.eventSource = eventSource;
  }

  public ZonedDateTime getEventTimeMin() {
    return eventTimeMin;
  }

  public void setEventTimeMin(ZonedDateTime eventTimeMin) {
    this.eventTimeMin = eventTimeMin;
  }

  public ZonedDateTime getEventTimeMax() {
    return eventTimeMax;
  }

  public void setEventTimeMax(ZonedDateTime eventTimeMax) {
    this.eventTimeMax = eventTimeMax;
  }

  public int getNumberOfEventsToGenerate() {
    return numberOfEventsToGenerate;
  }

  public void setNumberOfEventsToGenerate(int numberOfEventsToGenerate) {
    this.numberOfEventsToGenerate = numberOfEventsToGenerate;
  }

  public static class Builder {
    private EventSource eventSource;
    private ZonedDateTime eventTimeMin;
    private ZonedDateTime eventTimeMax;
    private int numberOfEventsToGenerate;

    public Builder createForEventSource(EventSource eventSource) {
      this.eventSource = eventSource;
      return this;
    }

    public Builder withEventTimeMin(ZonedDateTime eventTimeMin) {
      this.eventTimeMin = eventTimeMin;
      return this;
    }

    public Builder andEventTimeMax(ZonedDateTime eventTimeMax) {
      this.eventTimeMax = eventTimeMax;
      return this;
    }

    public Builder withNumberOfEventsToGenerate(int numberOfEventsToGenerate) {
      this.numberOfEventsToGenerate = numberOfEventsToGenerate;
      return this;
    }

    public InputGenerator build() {
      return new InputGenerator(
          this.eventSource, this.eventTimeMin, this.eventTimeMax, this.numberOfEventsToGenerate);
    }
  }
}
