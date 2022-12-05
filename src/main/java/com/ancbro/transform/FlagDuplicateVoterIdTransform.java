package com.ancbro.transform;

import com.ancbro.domain.Ballot;
import com.ancbro.transform.dofn.KeyByVoterIdDoFn;
import org.apache.beam.sdk.transforms.Count;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.PTransform;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.*;

public class FlagDuplicateVoterIdTransform
    extends PTransform<PCollection<Ballot>, PCollectionTuple> {

  public static final TupleTag<Long> LEGAL_VOTER_ID_TAG = new TupleTag<Long>() {};
  public static final TupleTag<Long> DUPLICATE_VOTER_ID_TAG = new TupleTag<Long>() {};

  @Override
  public PCollectionTuple expand(PCollection<Ballot> input) {
    return input
        .apply(ParDo.of(new KeyByVoterIdDoFn()))
        .apply(Count.perKey())
        .apply(
            ParDo.of(
                    new DoFn<KV<Long, Long>, Long>() {
                      @ProcessElement
                      public void processElement(ProcessContext c) {
                        Long voterID = c.element().getKey();
                        Long instancesOfVoterID = c.element().getValue();
                        if (instancesOfVoterID == 1) {
                          c.output(voterID);
                        } else {
                          c.output(DUPLICATE_VOTER_ID_TAG, voterID);
                        }
                      }
                    })
                .withOutputTags(LEGAL_VOTER_ID_TAG, TupleTagList.of(DUPLICATE_VOTER_ID_TAG)));
  }
}
