package com.ancbro.domain;

import com.ancbro.serde.ObjectMapperSingleton;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Assert;
import org.junit.Test;

public class BallotTest {

  @Test
  public void ballotShouldDeserialize() throws JsonProcessingException {
    String json =
        "{\"voterID\":1,\"ballotUUID\":\"6a1a7421-ce46-402b-b2de-4f4651737903\",\"eventTime\":\"2019-01-01T05:42Z\",\"candidate\":\"CANDIDATE_A\"}";

    Ballot ballot = ObjectMapperSingleton.INSTANCE.getObjectMapper().readValue(json, Ballot.class);

    System.out.println(ballot.toString());

    Assert.assertNotNull(ballot);
  }
}
