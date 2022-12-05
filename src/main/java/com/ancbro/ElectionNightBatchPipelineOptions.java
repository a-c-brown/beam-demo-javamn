package com.ancbro;

import org.apache.beam.sdk.options.Default;
import org.apache.beam.sdk.options.Description;
import org.apache.beam.sdk.options.PipelineOptions;

public interface ElectionNightBatchPipelineOptions extends PipelineOptions {

  @Description("Batch File Path")
  @Default.String("src/main/resources/fixed-batch-votes.txt")
  String getBatchData();

  void setBatchData(String input);

  @Description("Start Date")
  @Default.String("2020-11-01T00:00:00Z")
  String getStartDate();

  void setStartDate(String input);

  @Description("End Date")
  @Default.String("2020-11-01T23:59:00Z")
  String getEndDate();

  void setEndDate(String input);
}
