package com.ancbro.transform;

import com.ancbro.domain.Ballot;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.PTransform;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.PCollectionView;

import java.util.List;

public class FilterFraudulentBallotTransform
    extends PTransform<PCollection<Ballot>, PCollection<Ballot>> {

  private final PCollectionView<List<Long>> sideInputForFraudulentVoterIDs;
  private final PCollectionView<List<String>> sideInputForFraudulentBallotUUIDs;

  public FilterFraudulentBallotTransform(
      PCollectionView<List<Long>> sideInputForFraudulentVoterIDs,
      PCollectionView<List<String>> sideInputForFraudulentBallotUUIDs) {
    this.sideInputForFraudulentVoterIDs = sideInputForFraudulentVoterIDs;
    this.sideInputForFraudulentBallotUUIDs = sideInputForFraudulentBallotUUIDs;
  }

  @Override
  public PCollection<Ballot> expand(PCollection<Ballot> ballots) {
    return ballots
        .apply(
            ParDo.of(
                    new DoFn<Ballot, Ballot>() {
                      @ProcessElement
                      public void processElement(
                          @Element Ballot ballot, OutputReceiver<Ballot> out, ProcessContext c) {
                        List<Long> fraudulentVoterIDs = c.sideInput(sideInputForFraudulentVoterIDs);
                        if (!fraudulentVoterIDs.contains(ballot.getVoterID())) {
                          out.output(ballot);
                        }
                      }
                    })
                .withSideInputs(sideInputForFraudulentVoterIDs))
        .apply(
            ParDo.of(
                    new DoFn<Ballot, Ballot>() {
                      @ProcessElement
                      public void processElement(
                          @Element Ballot ballot, OutputReceiver<Ballot> out, ProcessContext c) {
                        List<String> fraudulentBallotUUIDs =
                            c.sideInput(sideInputForFraudulentBallotUUIDs);
                        if (!fraudulentBallotUUIDs.contains(ballot.getBallotUUID())) {
                          out.output(ballot);
                        }
                      }
                    })
                .withSideInputs(sideInputForFraudulentBallotUUIDs));
  }
}
