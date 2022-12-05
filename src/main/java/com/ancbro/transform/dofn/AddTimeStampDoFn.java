package com.ancbro.transform.dofn;

import com.ancbro.domain.Ballot;
import org.apache.beam.sdk.transforms.DoFn;
import org.joda.time.Instant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddTimeStampDoFn extends DoFn<Ballot, Ballot> {

  private static final Logger LOGGER = LoggerFactory.getLogger(AddTimeStampDoFn.class);

  @ProcessElement
  public void processElement(@Element Ballot element, OutputReceiver<Ballot> receiver) {

    Instant eventTime = null;

    try {
      eventTime = Instant.parse(element.getEventTime());
    } catch (Exception parsingException) {
      LOGGER.error("Unable to parse {}, {}", element.getEventTime(), parsingException);
    }

    receiver.outputWithTimestamp(element, eventTime);
  }
}
