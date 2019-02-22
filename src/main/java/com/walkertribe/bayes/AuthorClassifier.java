package com.walkertribe.bayes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import cc.mallet.classify.Classification;
import cc.mallet.classify.Classifier;
import cc.mallet.classify.NaiveBayesTrainer;
import cc.mallet.pipe.CharSequence2TokenSequence;
import cc.mallet.pipe.CharSequenceLowercase;
import cc.mallet.pipe.FeatureSequence2FeatureVector;
import cc.mallet.pipe.Pipe;
import cc.mallet.pipe.SerialPipes;
import cc.mallet.pipe.Target2Label;
import cc.mallet.pipe.TokenSequence2FeatureSequence;
import cc.mallet.pipe.TokenSequenceRemoveStopwords;
import cc.mallet.types.Instance;
import cc.mallet.types.InstanceList;
import cc.mallet.types.Labeling;

/**
 * Classifies Corpus objects by author.
 * @author Robert J. Walker
 */
public class AuthorClassifier {
  private Classifier classifier;

  /**
   * Creates a new, untrained AuthorClassifier.
   */
  public AuthorClassifier() {
    // do nothing
  }

  /**
   * Loads an AuthorClassifier from a file.
   */
  public AuthorClassifier(File file) throws IOException, ClassNotFoundException {
    try (
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
    ) {
      classifier = (Classifier) ois.readObject();
    }
  }

  /**
   * Trains the AuthorClassifier with the given training data.
   */
  public void train(Collection<Corpus> corpora) {
    Pipe pipe = buildPipe();
    InstanceList trainingData = new InstanceList(pipe);
    trainingData.addThruPipe(new CorpusIterator(corpora));
    classifier = new NaiveBayesTrainer().train(trainingData);
  }

  /**
   * Saves the AuthorClassifier to a file.
   */
  public void save(File file) throws IOException {
    try (
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
    ) {
      oos.writeObject(classifier);
    }
  }

  /**
   * Classifies the given Corpus.
   */
  public Classification classify(Corpus corpus) {
    if (classifier == null) {
      throw new IllegalStateException("Classifier has not been trained");
    }

    Pipe pipe = classifier.getInstancePipe();
    Iterator<Instance> iter = pipe.newIteratorFrom(new CorpusIterator(corpus));
    return classifier.classify(iter.next());
  }

  /**
   * Returns the data pipeline.
   */
  private Pipe buildPipe() {
    List<Pipe> pipeList = new ArrayList<>();
    pipeList.add(new Corpus2CharSequence());
    pipeList.add(new CharSequenceLowercase());
    pipeList.add(new CharSequence2TokenSequence(Pattern.compile("\\S+")));
    pipeList.add(new TokenSequenceRemoveStopwords(false, false));
    pipeList.add(new TokenSequence2FeatureSequence());
    pipeList.add(new Target2Label());
    pipeList.add(new FeatureSequence2FeatureVector());
    return new SerialPipes(pipeList);
  }

  /**
   * Prints Classification results to the console.
   */
  public static void printResult(Classification result) {
    Labeling labeling = result.getLabeling();
    int len = labeling.numLocations();
    DecimalFormat format = new DecimalFormat("0.0%");

    for (int rank = 0; rank < len; rank++) {
      System.out.println(
          "  " + labeling.getLabelAtRank(rank) + ": " +
          format.format(labeling.getValueAtRank(rank))
      );
    }
  }
}
