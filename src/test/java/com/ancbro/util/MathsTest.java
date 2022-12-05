package com.ancbro.util;

import org.junit.Assert;
import org.junit.Test;

import java.util.stream.IntStream;

public class MathsTest {

  @Test
  public void getRandomNumberInRangeShouldNotIncludeUpperBound() {
    IntStream.range(0, 10)
        .forEach(i -> Assert.assertEquals(1, Maths.GET_RANDOM_NUMBER_IN_RANGE.applyAsLong(1, 2)));
  }

  @Test
  public void getRandomWholeNumberGivenOneShouldReturnZero() {
    IntStream.range(0, 10)
        .forEach(
            i -> Assert.assertEquals(0, Maths.GET_RANDOM_WHOLE_NUMBER_LESS_THAN.applyAsInt(1)));
  }
}
