package com.ancbro.transform.dofn;

import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.PCollectionTuple;
import org.apache.beam.sdk.values.TupleTag;

public class ExtractDuplicateVoterIdDoFN extends DoFn<KV<Long, Long>, PCollectionTuple> {

  public static final TupleTag<Long> LEGAL_VOTER_ID_TAG = new TupleTag<Long>() {};
  public static final TupleTag<Long> DUPLICATE_VOTER_ID_TAG = new TupleTag<Long>() {};

  @ProcessElement
  public void processElement(
      @Element KV<Long, Long> voterID, MultiOutputReceiver multiOutputReceiver) {

    if (voterID.getValue() > 1) {
      multiOutputReceiver.get(DUPLICATE_VOTER_ID_TAG).output(voterID.getKey());
    }

    multiOutputReceiver.get(LEGAL_VOTER_ID_TAG).output(voterID.getKey());
  }
}
