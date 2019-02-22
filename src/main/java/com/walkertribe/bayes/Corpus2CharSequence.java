package com.walkertribe.bayes;

import java.io.Serializable;

import cc.mallet.pipe.Pipe;
import cc.mallet.types.Instance;

/**
 * A Pipe that pulls the text from a Corpus.
 * @author Robert J. Walker
 */
class Corpus2CharSequence extends Pipe implements Serializable {
  private static final long serialVersionUID = 1542056100608352013L;

  public Instance pipe(Instance carrier) {
    Object data = carrier.getData();

    if (data instanceof Corpus) {
      carrier.setData(((Corpus) data).getText());
    }

    return carrier;
  }
}
