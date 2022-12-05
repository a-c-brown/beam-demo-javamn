package com.ancbro.domain;

import org.apache.beam.sdk.coders.AvroCoder;
import org.apache.beam.sdk.coders.DefaultCoder;

import java.util.concurrent.atomic.AtomicLong;

// TODO :: REFACTOR FOR THREAD SAFETY

@DefaultCoder(AvroCoder.class)
public class Voter {

  private static final AtomicLong voterIdGenerator = new AtomicLong(1);
  private final AtomicLong voterId;

  public Voter() {
    this.voterId = new AtomicLong(voterIdGenerator.getAndIncrement());
  }

  public AtomicLong getVoterId() {
    return voterId;
  }
}
