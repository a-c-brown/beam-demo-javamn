package com.ancbro.slide_deck;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.coders.StringUtf8Coder;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.Count;
import org.apache.beam.sdk.transforms.Create;
import org.apache.beam.sdk.values.PCollection;

import java.util.Arrays;
import java.util.List;

/**
 * {@link PCollectionCreation} creates two {@link PCollection}s, one from memory using {@link
 * Create#of(java.lang.Iterable)} and one from a text file using {@link
 * TextIO.Read#from(java.lang.String)}
 *
 * <pre>{@code
 *     public class PCollectionCreation {
 *
 *     public static final List<String> LINES = Arrays.asList(
 *             "ELEMENT::fe634aaf-adbb-433c-b561-f84024b24737",
 *             "ELEMENT::b49cb08d-9ea6-47bc-94e3-fc0855e6e310",
 *             "ELEMENT::2ad5a015-5b99-4b50-ae72-b26d92908a12",
 *             "ELEMENT::e8bda2ba-1453-4047-9627-1796ce3ab106");
 *
 *     public static void main(String[] args) {
 *
 *         PipelineOptions options = PipelineOptionsFactory.create();
 *
 *         Pipeline pipeline = Pipeline.create(options);
 *
 *         PCollection<String> fromMemoryPCollection = pipeline.apply(Create.of(LINES)).setCoder(StringUtf8Coder.of());
 *
 *         fromMemoryPCollection.apply(Count.perElement());
 *
 *         PCollection<String> fromFilePCollection = pipeline.apply(TextIO.read().from("src/main/resources/slide-deck-demo-file.txt"));
 *
 *         fromFilePCollection.apply(Count.perElement());
 *
 *         pipeline.run();
 *
 *     }
 * }
 *
 * }</pre>
 */
public class PCollectionCreation {

  public static final List<String> LINES =
      Arrays.asList(
          "ELEMENT::fe634aaf-adbb-433c-b561-f84024b24737",
          "ELEMENT::b49cb08d-9ea6-47bc-94e3-fc0855e6e310",
          "ELEMENT::2ad5a015-5b99-4b50-ae72-b26d92908a12",
          "ELEMENT::e8bda2ba-1453-4047-9627-1796ce3ab106");

  public static void main(String[] args) {

    PipelineOptions options = PipelineOptionsFactory.create();
    Pipeline pipeline = Pipeline.create(options);

    PCollection<String> fromMemoryPCollection =
        pipeline.apply(Create.of(LINES)).setCoder(StringUtf8Coder.of());

    fromMemoryPCollection.apply(Count.perElement());

    PCollection<String> fromFilePCollection =
        pipeline.apply(TextIO.read().from("src/main/resources/slide-deck-demo-file.txt"));

    fromFilePCollection.apply(Count.perElement());

    pipeline.run();
  }
}
