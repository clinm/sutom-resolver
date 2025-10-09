package com.mclin.sutom.game.play.domain;

import com.mclin.sutom.game.play.domain.Attempt.AttemptBuilder;
import java.util.ArrayList;

public class Game {

  private SecretWord secretWord;

  public Game(SecretWord secretWord) {
    this.secretWord = secretWord;
  }

  public Hint hint() {
    Letter firstLetter = new Letter(secretWord.firstLetter());
    var letters = new ArrayList<Letter>();
    letters.add(firstLetter);

    secretWord
      .stream()
      .skip(1)
      .map(l -> Letter.DOT_UNKNOWN)
      .forEach(letters::add);

    return new Hint(letters);
  }

  public Attempt guess(Guess guess) {
    var builder = new AttemptBuilder();

    for (int i = 0; i < guess.value().length(); i++) {
      if (secretWord.hasCharacterAtPos(guess.at(i), i)) {
        builder.withWellPlaced(guess.at(i));
      } else {
        builder.withUnknown(guess.at(i));
      }
    }

    return builder.build();
  }
}
