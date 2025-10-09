package com.mclin.sutom.game.play.domain;

import java.util.ArrayList;
import java.util.Arrays;

public class Game {

  private SecretWord secretWord;

  public Game(SecretWord secretWord) {
    this.secretWord = secretWord;
  }

  public Hint hint() {
    Letter firstLetter = new Letter(secretWord.firstLetter());
    var letters = new ArrayList<>();
    letters.add(firstLetter);

    secretWord
      .stream()
      .skip(1)
      .map(l -> Letter.UNKNOWN)
      .forEach(letters::add);

    return new Hint(Arrays.asList(firstLetter, Letter.UNKNOWN));
  }
}
