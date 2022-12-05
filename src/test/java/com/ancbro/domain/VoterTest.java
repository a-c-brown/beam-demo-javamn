package com.ancbro.domain;

import org.junit.Assert;
import org.junit.Test;

public class VoterTest {

  @Test
  public void getVoterIdCalledSuccessivelyShouldReturnSuccessiveVoterIds() {
    Assert.assertEquals(1L, new Voter().getVoterId().get());
    Assert.assertEquals(2L, new Voter().getVoterId().get());
    Assert.assertEquals(3L, new Voter().getVoterId().get());
    Assert.assertEquals(4L, new Voter().getVoterId().get());
    Assert.assertEquals(5L, new Voter().getVoterId().get());
  }
}
