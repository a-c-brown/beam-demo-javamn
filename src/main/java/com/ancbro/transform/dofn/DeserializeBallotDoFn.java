package com.ancbro.transform.dofn;

import com.ancbro.domain.Ballot;
import com.ancbro.serde.ObjectMapperSingleton;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.beam.sdk.transforms.DoFn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeserializeBallotDoFn extends DoFn<String, Ballot> {

  private static final Logger LOGGER = LoggerFactory.getLogger(DeserializeBallotDoFn.class);

  @ProcessElement
  public void processElement(@Element String element, OutputReceiver<Ballot> receiver) {

    Ballot ballot = null;

    try {
      ballot = ObjectMapperSingleton.INSTANCE.getObjectMapper().readValue(element, Ballot.class);
    } catch (JsonProcessingException e) {
      LOGGER.error("Unable to deserialize {} to {}", element, Ballot.class.getSimpleName());
    }

    receiver.output(ballot);
  }
}
