package com.walkertribe.bayes;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import cc.mallet.types.Instance;

/**
 * Converts Corpus objects into Instances.
 * @author Robert J. Walker
 */
class CorpusIterator implements Iterator<Instance> {
  static Instance corpusToInstance(Corpus corpus) {
    return new Instance(corpus, corpus.getAuthor(), corpus.getTitle(), null);
  }

  private Iterator<Corpus> corporaIterator;

  /**
   * Produces a CorpusIterator that will convert a single Corpus.
   */
  CorpusIterator(Corpus corpus) {
    List<Corpus> list = new LinkedList<>();
    list.add(corpus);
    corporaIterator = list.iterator();
  }

  /**
   * Produces a CorpusIterator that will convert the given Collection of corpora.
   */
  CorpusIterator(Collection<Corpus> corpora) { 
    corporaIterator = corpora.iterator();
  }

  @Override
  public boolean hasNext() {
    return corporaIterator.hasNext();
  }

  @Override
  public Instance next() {
    return corpusToInstance(corporaIterator.next());
  }
}
