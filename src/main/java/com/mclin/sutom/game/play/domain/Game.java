package com.mclin.sutom.game.play.domain;

public class Game {

  private SecretWord secretWord;

  private Hint hint;

  private boolean win = false;

  public Game(SecretWord secretWord) {
    this.secretWord = secretWord;
    hint = secretWord.initialHint();
  }

  public Hint hint() {
    return hint;
  }

  public Attempt guess(Guess guess) {
    Attempt attempt = secretWord.guess(guess);
    win = attempt.win();
    hint = hint.union(attempt);
    return attempt;
  }

  public boolean win() {
    return win;
  }
}
