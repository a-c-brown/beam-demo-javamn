package com.ancbro.transform.dofn;

import com.ancbro.domain.Ballot;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.values.KV;

public class KeyByBallotIdDoFn extends DoFn<Ballot, KV<String, Ballot>> {

  @ProcessElement
  public void processElement(@Element Ballot ballot, OutputReceiver<KV<String, Ballot>> out) {
    out.output(KV.of(ballot.getBallotUUID(), ballot));
  }
}
