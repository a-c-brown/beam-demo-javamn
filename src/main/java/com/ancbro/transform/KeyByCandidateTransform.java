package com.ancbro.transform;

import com.ancbro.domain.Ballot;
import org.apache.beam.sdk.transforms.MapElements;
import org.apache.beam.sdk.transforms.PTransform;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.TypeDescriptors;

public class KeyByCandidateTransform
    extends PTransform<PCollection<Ballot>, PCollection<KV<String, String>>> {

  @Override
  public PCollection<KV<String, String>> expand(PCollection<Ballot> ballots) {
    return ballots.apply(
        MapElements.into(TypeDescriptors.kvs(TypeDescriptors.strings(), TypeDescriptors.strings()))
            .via((Ballot ballot) -> KV.of(ballot.getCandidate(), ballot.getBallotUUID())));
  }
}
