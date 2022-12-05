package com.ancbro.domain;

import org.apache.beam.sdk.coders.AvroCoder;
import org.apache.beam.sdk.coders.DefaultCoder;

@DefaultCoder(AvroCoder.class)
public enum Candidate {
  CANDIDATE_A,
  CANDIDATE_B
}
