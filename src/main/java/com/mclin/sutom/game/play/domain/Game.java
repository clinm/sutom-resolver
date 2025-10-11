package com.mclin.sutom.game.play.domain;

public class Game {

  private SecretWord secretWord;

  private Hint hint;

  public Game(SecretWord secretWord) {
    this.secretWord = secretWord;
    hint = secretWord.initialHint();
  }

  public Hint hint() {
    return hint;
  }

  public Attempt guess(Guess guess) {
    return secretWord.guess(guess);
  }
}
