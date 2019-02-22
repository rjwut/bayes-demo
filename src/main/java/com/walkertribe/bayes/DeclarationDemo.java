package com.walkertribe.bayes;

import java.io.File;
import java.io.IOException;

/**
 * Demonstrates using the MALLET library to perform naive Bayesian classification of documents. This
 * demo will load a previously-trained classifier that was created by BuildDemo, then classify the
 * Declaration of Independence to see if it is most similar to the works Shakespeare, Dickens, or
 * Twain.
 * @author Robert J. Walker
 */
public class DeclarationDemo {
  /**
   * Requires one argument: the path to the model file to load.
   */
  public static void main(String[] args) throws IOException, ClassNotFoundException {
    File modelFile = new File(args[0]);
    System.out.println("Loading classifier from " + modelFile + "...");
    AuthorClassifier classifier = new AuthorClassifier(modelFile);
    System.out.println("Classifier loaded.");
    Corpus testDeclaration = Corpus.fromUrl("The Declaration of Independence", null, "https://www.constitution.org/usdeclar.txt");
    AuthorClassifier.printResult(classifier.classify(testDeclaration));
  }
}
