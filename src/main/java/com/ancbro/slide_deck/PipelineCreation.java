package com.ancbro.slide_deck;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;

/**
 * {@link PipelineCreation} Defines the driver program to construct a beam workflow graph and create
 * a {@link Pipeline}
 *
 * <pre>{@code
 * public class CodeSnippetsPipeline {
 *
 *     public static void main(String[] args) {
 *
 *         PipelineOptions options = PipelineOptionsFactory.create();
 *
 *         Pipeline pipeline = Pipeline.create(options);
 *
 *         pipeline.run();
 *
 *     }
 *
 * }
 * }</pre>
 */
public class PipelineCreation {

  public static void main(String[] args) {

    PipelineOptions options = PipelineOptionsFactory.create();
    Pipeline pipeline = Pipeline.create(options);
    pipeline.run();
  }
}
