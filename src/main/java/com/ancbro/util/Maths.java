package com.ancbro.util;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.BiFunction;
import java.util.function.IntUnaryOperator;
import java.util.function.LongBinaryOperator;

public class Maths {

  public static final LongBinaryOperator GET_RANDOM_NUMBER_IN_RANGE =
      (min, max) -> ThreadLocalRandom.current().nextLong((max - min)) + min;
  public static final BiFunction<ZonedDateTime, ZonedDateTime, ZonedDateTime>
      GET_RANDOM_ZONED_DATE_TIME_BETWEEN =
          ((minZonedDateTime, maxZonedDateTime) -> {
            long min = Instant.from(minZonedDateTime).toEpochMilli();
            long max = Instant.from(maxZonedDateTime).toEpochMilli();
            long randomValue = GET_RANDOM_NUMBER_IN_RANGE.applyAsLong(min, max);
            return ZonedDateTime.ofInstant(Instant.ofEpochMilli(randomValue), ZoneId.of("UTC"));
          });
  private static final Random RANDOM = new Random();
  public static final IntUnaryOperator GET_RANDOM_WHOLE_NUMBER_LESS_THAN = RANDOM::nextInt;

  private Maths() {
    throw new IllegalStateException("Utility class");
  }
}
