package com.mclin.sutom.game.play.domain;

import com.mclin.sutom.game.play.domain.error.GameError;
import com.mclin.sutom.game.play.domain.error.NotInDictionaryError;
import com.mclin.sutom.game.play.domain.error.NotSameLengthError;
import com.mclin.sutom.shared.result.domain.Result;
import java.util.Optional;

public class Game {

  private DictionnaryRepository dictionnary;

  private SecretWord secretWord;

  private Hint hint;

  private Attempts attempts = Attempts.EMPTY;

  public Game(DictionnaryRepository dictionnary, SecretWord secretWord) {
    this.dictionnary = dictionnary;
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
    hint = hint.union(attempt);
    attempts = attempts.add(attempt);
    return Result.success(attempt);
  }

  private Optional<Result<Attempt, GameError>> validate(Guess guess) {
    int expected = secretWord.length();
    int actual = guess.length();

    if (expected != actual) {
      return Optional.of(Result.failure(new NotSameLengthError(expected, actual)));
    }

    if (dictionnary.contains(guess.value())) {
      return Optional.empty();
    }

    return Optional.of(Result.failure(new NotInDictionaryError(guess.value())));
  }

  public State state() {
    return attempts.state();
  }
}
