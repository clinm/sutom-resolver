package com.mclin.sutom.game.play.domain;

import com.mclin.sutom.game.play.domain.error.AttemptLimitReachedException;
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

  public Result<Attempt, GameError> guess(Guess guess) throws AttemptLimitReachedException {
    var validated = validate(guess);
    if (validated.isPresent()) {
      return validated.get();
    }
    return attempt(guess);
  }

  private Result<Attempt, GameError> attempt(Guess guess) throws AttemptLimitReachedException {
    Attempt attempt = secretWord.guess(guess);
    attempts = attempts.add(attempt);
    hint = hint.union(attempt);
    return Result.success(attempt);
  }

  private Optional<Result<Attempt, GameError>> validate(Guess guess) {
    int expected = secretWord.length();
    int actual = guess.length();

    if (expected != actual) {
      return Optional.of(Result.failure(new NotSameLengthError(expected, actual)));
    }

    if (dictionnary.contains(guess.word())) {
      return Optional.empty();
    }

    return Optional.of(Result.failure(new NotInDictionaryError(guess.value())));
  }

  public Hint hint() {
    return hint;
  }

  public Attempts attempts() {
    return attempts;
  }

  public State state() {
    return attempts.state();
  }
}
