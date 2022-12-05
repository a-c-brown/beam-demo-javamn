package com.ancbro.domain;

import org.apache.beam.sdk.coders.AvroCoder;
import org.apache.beam.sdk.coders.DefaultCoder;

import java.util.UUID;

/**
 * {@link Ballot} is a model for a document that records a vote in a simple election consisting of
 * only two candidates.
 *
 * <p>The ballot is considered fraudulent if two or more instances of voterId or ballotUUID exist
 * for all ballots cast.
 */
@DefaultCoder(AvroCoder.class)
public final class Ballot {

  private final Long voterID;
  private final String ballotUUID;
  private final String eventTime;
  private final String candidate;

  public Ballot() {
    voterID = null;
    ballotUUID = null;
    eventTime = null;
    candidate = null;
  }

  /**
   * @param voterID the unique identifier for a voter - fraudulent if duplicated
   * @param eventTime the moment the vote is cast - generated when the ballot is inserted into the
   *     ballot box
   * @param candidate the candidate voted for
   */
  public Ballot(Long voterID, String eventTime, String candidate) {
    this.voterID = voterID;
    this.ballotUUID = UUID.randomUUID().toString();
    this.eventTime = eventTime;
    this.candidate = candidate;
  }

  public Long getVoterID() {
    return voterID;
  }

  public String getBallotUUID() {
    return ballotUUID;
  }

  public String getEventTime() {
    return eventTime;
  }

  public String getCandidate() {
    return candidate;
  }

  @Override
  public String toString() {
    return "Ballot{"
        + "voterID="
        + voterID
        + ", ballotUUID='"
        + ballotUUID
        + '\''
        + ", eventTime='"
        + eventTime
        + '\''
        + ", candidate='"
        + candidate
        + '\''
        + '}';
  }
}
