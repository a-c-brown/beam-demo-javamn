package com.ancbro.demo;

import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.PCollectionTuple;
import org.apache.beam.sdk.values.TupleTag;
import org.apache.beam.sdk.values.TupleTagList;

/** https://beam.apache.org/releases/javadoc/2.0.0/org/apache/beam/sdk/transforms/ParDo.html */
public class AdditionalOutput {

  public static void main(String[] args) {

    PCollection<String> words = null;

    final int wordLengthCutOff = 10;

    final TupleTag<String> wordsBelowCutOffTag = new TupleTag<String>() {};
    final TupleTag<Integer> wordLengthsAboveCutOffTag = new TupleTag<Integer>() {};
    final TupleTag<String> markedWordsTag = new TupleTag<String>() {};

    PCollectionTuple results =
        words.apply(
            ParDo.of(
                    new DoFn<String, String>() {

                      final TupleTag<String> specialWordsTag = new TupleTag<String>() {};

                      @ProcessElement
                      public void processElement(ProcessContext c) {
                        String word = c.element();
                        if (word.length() <= wordLengthCutOff) {
                          // Emit this short word to the main output.
                          c.output(word);
                        } else {
                          // Emit this long word's length to a specified output.
                          c.output(wordLengthsAboveCutOffTag, word.length());
                        }
                        if (word.startsWith("MARKER")) {
                          // Emit this word to a different specified output.
                          c.output(markedWordsTag, word);
                        }
                        if (word.startsWith("SPECIAL")) {
                          // Emit this word to the unconsumed output.
                          c.output(specialWordsTag, word);
                        }
                      }
                    })
                .withOutputTags(
                    wordsBelowCutOffTag,
                    TupleTagList.of(wordLengthsAboveCutOffTag).and(markedWordsTag)));

    // Extract the PCollection results, by tag.
    PCollection<String> wordsBelowCutOff = results.get(wordsBelowCutOffTag);
    PCollection<Integer> wordLengthsAboveCutOff = results.get(wordLengthsAboveCutOffTag);
    PCollection<String> markedWords = results.get(markedWordsTag);
  }
}
