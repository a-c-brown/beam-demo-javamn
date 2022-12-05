package com.ancbro.transform;

import com.ancbro.domain.Ballot;
import com.ancbro.transform.dofn.KeyByBallotIdDoFn;
import org.apache.beam.sdk.transforms.Count;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.PTransform;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.*;

public class FlagDuplicateBallotIdTransform
    extends PTransform<PCollection<Ballot>, PCollectionTuple> {

  public static final TupleTag<String> LEGAL_BALLOT_ID_TAG = new TupleTag<String>() {};
  public static final TupleTag<String> DUPLICATE_BALLOT_ID_TAG = new TupleTag<String>() {};

  @Override
  public PCollectionTuple expand(PCollection<Ballot> input) {
    return input
        .apply(ParDo.of(new KeyByBallotIdDoFn()))
        .apply(Count.perKey())
        .apply(
            ParDo.of(
                    new DoFn<KV<String, Long>, String>() {
                      @ProcessElement
                      public void processElement(ProcessContext c) {
                        String ballotID = c.element().getKey();
                        Long instancesOfBallotID = c.element().getValue();
                        if (instancesOfBallotID == 1) {
                          c.output(ballotID);
                        } else {
                          c.output(DUPLICATE_BALLOT_ID_TAG, ballotID);
                        }
                      }
                    })
                .withOutputTags(LEGAL_BALLOT_ID_TAG, TupleTagList.of(DUPLICATE_BALLOT_ID_TAG)));
  }
}
