package com.ancbro;

import com.ancbro.domain.Ballot;
import com.ancbro.transform.FilterFraudulentBallotTransform;
import com.ancbro.transform.FlagDuplicateBallotIdTransform;
import com.ancbro.transform.FlagDuplicateVoterIdTransform;
import com.ancbro.transform.KeyByCandidateTransform;
import com.ancbro.transform.dofn.AddTimeStampDoFn;
import com.ancbro.transform.dofn.DeserializeBallotDoFn;
import com.ancbro.transform.dofn.LogOutputDoFn;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.Count;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.transforms.View;
import org.apache.beam.sdk.transforms.windowing.FixedWindows;
import org.apache.beam.sdk.transforms.windowing.Window;
import org.apache.beam.sdk.values.PCollection;
import org.joda.time.Duration;

import static com.ancbro.ElectionNightBatchPipeline.TransformName.*;
import static com.ancbro.transform.FlagDuplicateBallotIdTransform.DUPLICATE_BALLOT_ID_TAG;
import static com.ancbro.transform.FlagDuplicateVoterIdTransform.DUPLICATE_VOTER_ID_TAG;

public class ElectionNightBatchPipeline {

  public static void main(String[] args) {

    PipelineOptionsFactory.register(ElectionNightBatchPipelineOptions.class);

    ElectionNightBatchPipelineOptions electionNightBatchPipelineOptions =
        PipelineOptionsFactory.fromArgs(args)
            .withValidation()
            .as(ElectionNightBatchPipelineOptions.class);

    Pipeline pipeline = Pipeline.create(electionNightBatchPipelineOptions);

    PCollection<Ballot> rawBallots =
        pipeline
            .apply(
                READ_BALLOTS, TextIO.read().from(electionNightBatchPipelineOptions.getBatchData()))
            .apply(DESERIALIZE_BALLOTS, ParDo.of(new DeserializeBallotDoFn()))
            .apply(APPLY_TIMESTAMPS, ParDo.of(new AddTimeStampDoFn()))
            .apply(APPLY_WINDOWING, Window.into(FixedWindows.of(Duration.standardDays(1))));

    PCollection<Long> fraudulentVoterIDs =
        rawBallots
            .apply(FLAG_FRAUDULENT_VOTER_IDS, new FlagDuplicateVoterIdTransform())
            .get(DUPLICATE_VOTER_ID_TAG);

    PCollection<String> fraudulentBallotUUIDs =
        rawBallots
            .apply(FLAG_FRAUDULENT_BALLOT_UUIDS, new FlagDuplicateBallotIdTransform())
            .get(DUPLICATE_BALLOT_ID_TAG);

    rawBallots
        .apply(
            FILTER_FRAUDULENT_BALLOTS,
            new FilterFraudulentBallotTransform(
                fraudulentVoterIDs.apply(View.asList()),
                fraudulentBallotUUIDs.apply(View.asList())))
        .apply(KEY_BY_CANDIDATE, new KeyByCandidateTransform())
        .apply(COUNT_BALLOTS, Count.perKey())
        .apply(LOG_OUTPUT, ParDo.of(new LogOutputDoFn()));

    pipeline.run().waitUntilFinish();
  }

  static class TransformName {

    static final String READ_BALLOTS = "Read Ballots";
    static final String DESERIALIZE_BALLOTS = "Deserialize Ballots";
    static final String APPLY_TIMESTAMPS = "Apply Timestamps";
    static final String APPLY_WINDOWING = "Apply Windowing";
    static final String KEY_BY_CANDIDATE = "Key By Candidate";
    static final String COUNT_BALLOTS = "Count Ballots";
    static final String LOG_OUTPUT = "Log Output";
    static final String FLAG_FRAUDULENT_VOTER_IDS = "Flag Fraudulent Voter IDs";
    static final String FLAG_FRAUDULENT_BALLOT_UUIDS = "Flag Fraudulent Ballot UUIDs";
    static final String FILTER_FRAUDULENT_BALLOTS = "Filter Fraudulent Ballots";

    private TransformName() {
      throw new AssertionError("Non-instantiable");
    }
  }
}
