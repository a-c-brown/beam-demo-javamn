package com.ancbro.slide_deck;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.coders.StringUtf8Coder;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.Create;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.PCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

/**
 * A simple example of {@link ParDo}. User defined code is passed to {@link ParDo} via a new {@link
 * DoFn} in which the {@link DoFn.ProcessElement} denotes the behavior to applied to the elements of
 * the {@link PCollection}
 *
 * <p>This Transform simply returns a {@link String#substring(int)} consisting of the UUID of the
 * element.
 *
 * <pre>{@code
 * public static final List<String> LINES = Arrays.asList(
 *             "ELEMENT::fe634aaf-adbb-433c-b561-f84024b24737",
 *             "ELEMENT::b49cb08d-9ea6-47bc-94e3-fc0855e6e310");
 *
 *     public static void main(String[] args) {
 *
 *         PipelineOptions options = PipelineOptionsFactory.create();
 *         Pipeline pipeline = Pipeline.create(options);
 *         PCollection<String> fromMemoryPCollection = pipeline.apply(Create.of(LINES)).setCoder(StringUtf8Coder.of());
 *         fromMemoryPCollection.apply(ParDo.of(new DoFn<String, String>() {
 *
 *             {@literal @}ProcessElement
 *             public void processElement(@Element String element, OutputReceiver<String> out) {
 *
 *                 String uuid = extractElementUUID(element);
 *                 out.output(uuid);
 *             }
 *         }));
 *         pipeline.run();
 *     }
 *
 * }</pre>
 */
public class PTransformCreation {

  public static final List<String> LINES =
      Arrays.asList(
          "ELEMENT::fe634aaf-adbb-433c-b561-f84024b24737",
          "ELEMENT::b49cb08d-9ea6-47bc-94e3-fc0855e6e310");
  private static final Logger LOGGER = LoggerFactory.getLogger(PTransformCreation.class);

  public static void main(String[] args) {

    PipelineOptions options = PipelineOptionsFactory.create();
    Pipeline pipeline = Pipeline.create(options);
    PCollection<String> fromMemoryPCollection =
        pipeline.apply(Create.of(LINES)).setCoder(StringUtf8Coder.of());

    fromMemoryPCollection.apply(
        ParDo.of(
            new DoFn<String, String>() {

              @ProcessElement
              public void processElement(@Element String element, OutputReceiver<String> out) {

                String uuid = extractElementUUID(element);

                LOGGER.info(uuid);

                out.output(uuid);
              }
            }));
    pipeline.run();
  }

  private static String extractElementUUID(String element) {
    return element.substring(8);
  }
}
