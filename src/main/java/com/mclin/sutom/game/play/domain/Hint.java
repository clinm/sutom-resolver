package com.mclin.sutom.game.play.domain;

import com.mclin.sutom.shared.error.domain.Assert;
import java.util.ArrayList;
import java.util.List;

public record Hint(List<Letter> letters) {
  public Hint {
    Assert.notEmpty("letters", letters);
  }

  public Hint union(Attempt attempt) {
    var newLetters = new ArrayList<Letter>();

    for (int i = 0; i < attempt.letters().size(); i++) {
      Letter attemptLetter = attempt.at(i);
      if (attemptLetter.isWellPlaced()) {
        newLetters.add(attemptLetter);
      } else {
        newLetters.add(letters.get(i));
      }
    }
    return new Hint(newLetters);
  }
}
