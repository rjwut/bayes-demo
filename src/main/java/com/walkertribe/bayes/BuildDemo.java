package com.walkertribe.bayes;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Demonstrates using the MALLET library to perform naive Bayesian classification of documents. This
 * demo will create a classifier and train it with a works by Shakespeare, Dickens, and Twain. It
 * will then save the model to a file, then test the classifier with a work by each author that was
 * not used in the training.
 * @author Robert J. Walker
 */
public class BuildDemo {
  private static List<Corpus> CORPORA = new LinkedList<>(Arrays.asList(new Corpus[] {
      Corpus.fromUrl("All's Well that Ends Well", "William Shakespeare", "http://textfiles.com/etext/AUTHORS/SHAKESPEARE/shakespeare-alls-11.txt"),
      Corpus.fromUrl("Antony and Cleopatra", "William Shakespeare", "http://textfiles.com/etext/AUTHORS/SHAKESPEARE/shakespeare-antony-23.txt"),
      Corpus.fromUrl("As You Like It", "William Shakespeare", "http://textfiles.com/etext/AUTHORS/SHAKESPEARE/shakespeare-as-12.txt"),
      Corpus.fromUrl("The Comedy of Errors", "William Shakespeare", "http://textfiles.com/etext/AUTHORS/SHAKESPEARE/shakespeare-comedy-7.txt"),
      Corpus.fromUrl("Coriolanus", "William Shakespeare", "http://textfiles.com/etext/AUTHORS/SHAKESPEARE/shakespeare-coriolanus-24.txt"),
      Corpus.fromUrl("Cymbeline", "William Shakespeare", "http://textfiles.com/etext/AUTHORS/SHAKESPEARE/shakespeare-cymbeline-17.txt"),
      Corpus.fromUrl("King Henry IV", "William Shakespeare", "http://textfiles.com/etext/AUTHORS/SHAKESPEARE/shakespeare-first-51.txt"),
      Corpus.fromUrl("Hamlet", "William Shakespeare", "http://textfiles.com/etext/AUTHORS/SHAKESPEARE/shakespeare-hamlet-25.txt"),
      Corpus.fromUrl("Julius Caesar", "William Shakespeare", "http://textfiles.com/etext/AUTHORS/SHAKESPEARE/shakespeare-julius-26.txt"),
      Corpus.fromUrl("King Lear", "William Shakespeare", "http://textfiles.com/etext/AUTHORS/SHAKESPEARE/shakespeare-king-45.txt"),
      Corpus.fromUrl("King Henry V", "William Shakespeare", "http://textfiles.com/etext/AUTHORS/SHAKESPEARE/shakespeare-life-54.txt"),
      Corpus.fromUrl("King Henry VIII", "William Shakespeare", "http://textfiles.com/etext/AUTHORS/SHAKESPEARE/shakespeare-life-55.txt"),
      Corpus.fromUrl("King John", "William Shakespeare", "http://textfiles.com/etext/AUTHORS/SHAKESPEARE/shakespeare-life-56.txt"),
      Corpus.fromUrl("A Lover's Complaint", "William Shakespeare", "http://textfiles.com/etext/AUTHORS/SHAKESPEARE/shakespeare-lovers-62.txt"),
      Corpus.fromUrl("Love's Labour's Lost", "William Shakespeare", "http://textfiles.com/etext/AUTHORS/SHAKESPEARE/shakespeare-loves-8.txt"),
      Corpus.fromUrl("Macbeth", "William Shakespeare", "http://textfiles.com/etext/AUTHORS/SHAKESPEARE/shakespeare-macbeth-46.txt"),
      Corpus.fromUrl("Measure for Measure", "William Shakespeare", "http://textfiles.com/etext/AUTHORS/SHAKESPEARE/shakespeare-measure-13.txt"),
      Corpus.fromUrl("The Merchant of Venice", "William Shakespeare", "http://textfiles.com/etext/AUTHORS/SHAKESPEARE/shakespeare-merchant-5.txt"),
      Corpus.fromUrl("The Merry Wives of Windsor", "William Shakespeare", "http://textfiles.com/etext/AUTHORS/SHAKESPEARE/shakespeare-merry-15.txt"),
      Corpus.fromUrl("A Midsummer Night's Dream", "William Shakespeare", "http://textfiles.com/etext/AUTHORS/SHAKESPEARE/shakespeare-midsummer-16.txt"),
      Corpus.fromUrl("Much Ado About Nothing", "William Shakespeare", "http://textfiles.com/etext/AUTHORS/SHAKESPEARE/shakespeare-much-3.txt"),
      Corpus.fromUrl("Othello", "William Shakespeare", "http://textfiles.com/etext/AUTHORS/SHAKESPEARE/shakespeare-othello-47.txt"),
      Corpus.fromUrl("Pericles, Prince of Tyre", "William Shakespeare", "http://textfiles.com/etext/AUTHORS/SHAKESPEARE/shakespeare-pericles-21.txt"),
      Corpus.fromUrl("The Rape of Lucrece", "William Shakespeare", "http://textfiles.com/etext/AUTHORS/SHAKESPEARE/shakespeare-rape-61.txt"),
      Corpus.fromUrl("Romeo and Juliet", "William Shakespeare", "http://textfiles.com/etext/AUTHORS/SHAKESPEARE/shakespeare-romeo-48.txt"),
      Corpus.fromUrl("King Henry IV", "William Shakespeare", "http://textfiles.com/etext/AUTHORS/SHAKESPEARE/shakespeare-second-52.txt"),
      Corpus.fromUrl("Collection of Shakespeare Sonnets", "William Shakespeare", "http://textfiles.com/etext/AUTHORS/SHAKESPEARE/shakespeare-sonnets-59.txt"),
      Corpus.fromUrl("The Taming of the Shrew", "William Shakespeare", "http://textfiles.com/etext/AUTHORS/SHAKESPEARE/shakespeare-taming-2.txt"),
      Corpus.fromUrl("The Tempest", "William Shakespeare", "http://textfiles.com/etext/AUTHORS/SHAKESPEARE/shakespeare-tempest-4.txt"),
      Corpus.fromUrl("King Henry VI", "William Shakespeare", "http://textfiles.com/etext/AUTHORS/SHAKESPEARE/shakespeare-third-53.txt"),
      Corpus.fromUrl("Timon of Athens", "William Shakespeare", "http://textfiles.com/etext/AUTHORS/SHAKESPEARE/shakespeare-timon-49.txt"),
      Corpus.fromUrl("Titus Andronicus", "William Shakespeare", "http://textfiles.com/etext/AUTHORS/SHAKESPEARE/shakespeare-titus-50.txt"),
      Corpus.fromUrl("King Richard II", "William Shakespeare", "http://textfiles.com/etext/AUTHORS/SHAKESPEARE/shakespeare-tragedy-57.txt"),
      Corpus.fromUrl("King Richard III", "William Shakespeare", "http://textfiles.com/etext/AUTHORS/SHAKESPEARE/shakespeare-tragedy-58.txt"),
      Corpus.fromUrl("Troilus and Cressida", "William Shakespeare", "http://textfiles.com/etext/AUTHORS/SHAKESPEARE/shakespeare-troilus-22.txt"),
      Corpus.fromUrl("Twelfth Night", "William Shakespeare", "http://textfiles.com/etext/AUTHORS/SHAKESPEARE/shakespeare-twelfth-20.txt"),
      Corpus.fromUrl("The Two Gentlemen of Verona", "William Shakespeare", "http://textfiles.com/etext/AUTHORS/SHAKESPEARE/shakespeare-two-18.txt"),
      Corpus.fromUrl("Venus and Adonis", "William Shakespeare", "http://textfiles.com/etext/AUTHORS/SHAKESPEARE/shakespeare-venus-60.txt"),

      Corpus.fromUrl("American Notes for General Circulation", "Charles Dickens", "http://textfiles.com/etext/AUTHORS/DICKENS/dickens-american-631.txt"),
      Corpus.fromUrl("The Battle of Life", "Charles Dickens", "http://textfiles.com/etext/AUTHORS/DICKENS/dickens-battle-630.txt"),
      Corpus.fromUrl("A Child's History of England", "Charles Dickens", "http://textfiles.com/etext/AUTHORS/DICKENS/dickens-childs-629.txt"),
      Corpus.fromUrl("The Chimes", "Charles Dickens", "http://textfiles.com/etext/AUTHORS/DICKENS/dickens-chimes-379.txt"),
      Corpus.fromUrl("A Christmas Carol", "Charles Dickens", "http://textfiles.com/etext/AUTHORS/DICKENS/dickens-christmas-125.txt"),
      Corpus.fromUrl("The Cricket on the Hearth", "Charles Dickens", "http://textfiles.com/etext/AUTHORS/DICKENS/dickens-cricket-127.txt"),
      Corpus.fromUrl("David Copperfield", "Charles Dickens", "http://textfiles.com/etext/AUTHORS/DICKENS/dickens-david-626.txt"),
      Corpus.fromUrl("Dombey and Son", "Charles Dickens", "http://textfiles.com/etext/AUTHORS/DICKENS/dickens-dombey-622.txt"),
      Corpus.fromUrl("Hard Times", "Charles Dickens", "http://textfiles.com/etext/AUTHORS/DICKENS/dickens-hard-625.txt"),
      Corpus.fromUrl("The Haunted Man and the Ghost's Bargain", "Charles Dickens", "http://textfiles.com/etext/AUTHORS/DICKENS/dickens-haunted-633.txt"),
      Corpus.fromUrl("Holiday Romance", "Charles Dickens", "http://textfiles.com/etext/AUTHORS/DICKENS/dickens-holiday-623.txt"),
      Corpus.fromUrl("Hunted Down", "Charles Dickens", "http://textfiles.com/etext/AUTHORS/DICKENS/dickens-hunted-624.txt"),
      Corpus.fromUrl("Master Humphrey's Clock", "Charles Dickens", "http://textfiles.com/etext/AUTHORS/DICKENS/dickens-master-634.txt"),
      Corpus.fromUrl("The Mystery of Edwin Drood", "Charles Dickens", "http://textfiles.com/etext/AUTHORS/DICKENS/dickens-mystery-636.txt"),
      Corpus.fromUrl("The Old Curiosity Shop", "Charles Dickens", "http://textfiles.com/etext/AUTHORS/DICKENS/dickens-old-628.txt"),
      Corpus.fromUrl("Oliver Twist", "Charles Dickens", "http://textfiles.com/etext/AUTHORS/DICKENS/dickens-oliver-627.txt"),
      Corpus.fromUrl("The Pickwick Papers", "Charles Dickens", "http://textfiles.com/etext/AUTHORS/DICKENS/dickens-pickwick-635.txt"),
      Corpus.fromUrl("Pictures from Italy", "Charles Dickens", "http://textfiles.com/etext/AUTHORS/DICKENS/dickens-pictures-632.txt"),
      Corpus.fromUrl("Speeches: Literary and Social", "Charles Dickens", "http://textfiles.com/etext/AUTHORS/DICKENS/dickens-speeches-621.txt"),

      Corpus.fromUrl("A Ghost Story", "Mark Twain", "http://textfiles.com/etext/AUTHORS/TWAIN/ghost.mt"),
      Corpus.fromUrl("The Adventures of Tom Sawyer", "Mark Twain", "http://textfiles.com/etext/AUTHORS/TWAIN/twain-adventures-27.txt"),
      Corpus.fromUrl("The Adventures of Huckleberry Finn", "Mark Twain", "http://textfiles.com/etext/AUTHORS/TWAIN/twain-adventures-28.txt"),
      Corpus.fromUrl("A Connecticut Yankee in King Arthur's Court", "Mark Twain", "http://textfiles.com/etext/AUTHORS/TWAIN/twain-connecticut-31.txt"),
      Corpus.fromUrl("Extracts from Adam's Diary", "Mark Twain", "http://textfiles.com/etext/AUTHORS/TWAIN/twain-extracts-32.txt"),
      Corpus.fromUrl("The Great Revolution in Pitcairn", "Mark Twain", "http://textfiles.com/etext/AUTHORS/TWAIN/twain-great-34.txt"),
      Corpus.fromUrl("A Collection of Mark Twain's Speeches", "Mark Twain", "http://textfiles.com/etext/AUTHORS/TWAIN/twain-mark-414.txt"),
      Corpus.fromUrl("My Watch: An Instructive Little Talk", "Mark Twain", "http://textfiles.com/etext/AUTHORS/TWAIN/twain-my-35.txt"),
      Corpus.fromUrl("A New Crime", "Mark Twain", "http://textfiles.com/etext/AUTHORS/TWAIN/twain-new-36.txt"),
      Corpus.fromUrl("Niagra", "Mark Twain", "http://textfiles.com/etext/AUTHORS/TWAIN/twain-niagara-37.txt"),
      Corpus.fromUrl("Political Enconomy", "Mark Twain", "http://textfiles.com/etext/AUTHORS/TWAIN/twain-political-38.txt"),
      Corpus.fromUrl("The Prince and the Pauper", "Mark Twain", "http://textfiles.com/etext/AUTHORS/TWAIN/twain-prince-30.txt"),
      Corpus.fromUrl("Puddn'head Wilson", "Mark Twain", "http://textfiles.com/etext/AUTHORS/TWAIN/twain-puddnhead-29.txt"),
      Corpus.fromUrl("Tom Saywer Abroad", "Mark Twain", "http://textfiles.com/etext/AUTHORS/TWAIN/twain-tom-39.txt"),
      Corpus.fromUrl("Tom Sawyer, Detective", "Mark Twain", "http://textfiles.com/etext/AUTHORS/TWAIN/twain-tom-40.txt"),
      Corpus.fromUrl("A Tramp Abroad", "Mark Twain", "http://textfiles.com/etext/AUTHORS/TWAIN/twain-tramp-41.txt")
  }));

  /**
   * Requires one argument: the path to the model file to save.
   */
  public static void main(String[] args) throws IOException {
    File modelFile = new File(args[0]);
    AuthorClassifier classifier = new AuthorClassifier();
    System.out.println("Training...");
    classifier.train(CORPORA);
    System.out.print("Saving classifier to " + modelFile + "...");
    classifier.save(modelFile);
    System.out.println(" saved.");
    System.out.println();
    System.out.println("Testing...");
    Corpus testShakespeare = Corpus.fromUrl("The Winter's Tale", null, "http://textfiles.com/etext/AUTHORS/SHAKESPEARE/shakespeare-winters-19.txt");
    Corpus testTwain = Corpus.fromUrl("What is Man?", null, "http://textfiles.com/etext/AUTHORS/TWAIN/twain-what-42.txt");
    Corpus testDickens = Corpus.fromUrl("The Tale of Two Cities", null, "http://textfiles.com/etext/AUTHORS/DICKENS/dickens-tale-126.txt");
    System.out.println();
    AuthorClassifier.printResult(classifier.classify(testShakespeare));
    System.out.println();
    AuthorClassifier.printResult(classifier.classify(testDickens));
    System.out.println();
    AuthorClassifier.printResult(classifier.classify(testTwain));
  }
}
