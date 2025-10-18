package com.mclin.sutom.game.play.domain;

import com.mclin.sutom.game.play.domain.error.GameError;
import com.mclin.sutom.game.play.domain.error.NotSameLengthError;
import com.mclin.sutom.shared.result.domain.Result;
import java.util.List;
import java.util.Optional;

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

  public Result<Attempt, GameError> guess(Guess guess) {
    return validate(guess).orElseGet(() -> attempt(guess));
  }

  private Result<Attempt, GameError> attempt(Guess guess) {
    Attempt attempt = secretWord.guess(guess);
    win = attempt.win();
    hint = hint.union(attempt);
    return Result.success(attempt);
  }

  private Optional<Result<Attempt, GameError>> validate(Guess guess) {
    int expected = secretWord.length();
    int actual = guess.length();

    if (expected == actual) {
      return Optional.empty();
    }

    List<GameError> errors = List.of(new NotSameLengthError(expected, actual));
    return Optional.of(Result.failure(errors));
  }

  public boolean win() {
    return win;
  }
}
