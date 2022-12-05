package com.ancbro.transform.dofn;

import com.ancbro.domain.Ballot;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.values.KV;

public class KeyByVoterIdDoFn extends DoFn<Ballot, KV<Long, Ballot>> {

  @ProcessElement
  public void processElement(@Element Ballot ballot, OutputReceiver<KV<Long, Ballot>> out) {
    out.output(KV.of(ballot.getVoterID(), ballot));
  }
}
