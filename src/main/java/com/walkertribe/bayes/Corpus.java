package com.walkertribe.bayes;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * A document.
 * @author Robert J. Walker
 */
class Corpus {
  /**
   * Creates a Corpus from a URL.
   */
  static Corpus fromUrl(String title, String author, String url) {
    return new Corpus(title, author, url, null);
  }

  /**
   * Creates a Corpus from a String.
   */
  static Corpus fromString(String title, String author, String text) {
    return new Corpus(title, author, null, text);
  }

  private String title;
  private String author;
  private String url;
  private String text;

  /**
   * Creates a new Corpus. The author can be null for corpora to be classified.
   */
  private Corpus(String title, String author, String url, String text) {
    this.title = title;
    this.author = author;
    this.url = url;
    this.text = text;
  }

  /**
   * Returns the title of this Corpus.
   */
  public String getTitle() {
    return title;
  }

  /**
   * Returns the author of this Corpus, or null if it is unknown.
   */
  public String getAuthor() {
    return author;
  }

  /**
   * Returns the text of this Corpus. If it hasn't be previously loaded, this will download it.
   */
  String getText() {
    if (text == null) {
      loadText();
    }

    return text;
  }

  /**
   * Downloads the text of this Corpus.
   */
  private void loadText() {
    System.out.println("Loading " + this + "...");

    try (Scanner scanner = new Scanner(
        new URL(url).openStream(), StandardCharsets.UTF_8.toString()
    )) {
      scanner.useDelimiter("\\A");
      text = scanner.hasNext() ? scanner.next() : "";
    } catch (IOException ex) {
      throw new RuntimeException(ex);
    }

    if (text.isBlank()) {
      throw new RuntimeException("No data found at URL: " + url);
    }
  }

  @Override
  public String toString() {
    return "\"" + title + "\"" + (author != null ? " by " + author : "");
  }
}