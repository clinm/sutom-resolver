package com.mclin.sutom.game.play.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mclin.sutom.UnitTest;
import com.mclin.sutom.game.play.domain.Attempt.AttemptBuilder;
import com.mclin.sutom.game.play.domain.error.GameError;
import com.mclin.sutom.game.play.domain.error.NotSameLengthError;
import com.mclin.sutom.shared.result.domain.Result;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@UnitTest
class GameTest {

  Game game;

  Result<Attempt, GameError> result;

  @Nested
  class CreateHint {

    @Test
    void initialHint() {
      givenGame("HI");

      var hintLetters = Arrays.asList(Letter.wellPlaced('H'), Letter.DOT_UNKNOWN);
      expectHint(hintLetters);
    }

    @Test
    void afterOneGuessWithWellPlaced() {
      givenGame("BYE");

      whenGuess("BYR");

      var hintLetters = Arrays.asList(Letter.wellPlaced('B'), Letter.wellPlaced('Y'), Letter.DOT_UNKNOWN);
      expectHint(hintLetters);
    }

    private void expectHint(List<Letter> hintLetters) {
      assertThat(game.hint()).isEqualTo(new Hint(hintLetters));
    }
  }

  @Nested
  class GuessWord {

    @Test
    void newGameIsNotWin() {
      givenGame("HI");

      expectWin(false);
    }

    @Test
    void incorrectAttemptIsNotWin() {
      givenGame("HI");

      whenGuess("HA");

      expectWin(false);
    }

    @Test
    void correctWord() {
      givenGame("HI");

      // @formatter:off
      Attempt expected = new AttemptBuilder()
        .withWellPlaced('H')
        .withWellPlaced('I')
        .build();
      // @formatter:on

      whenGuess("HI");

      expectAttempt(expected);
    }

    @Test
    void unknownLetter() {
      givenGame("HI");

      // @formatter:off
      Attempt expected = new AttemptBuilder()
        .withWellPlaced('H')
        .withUnknown('A')
        .build();
      // @formatter:on

      whenGuess("HA");

      expectAttempt(expected);
    }

    @Test
    void wrongPlacedLetter() {
      givenGame("HI");

      // @formatter:off
      Attempt expected = new AttemptBuilder()
        .withMisPlaced('I')
        .withMisPlaced('H')
        .build();
      // @formatter:on

      whenGuess("IH");

      expectAttempt(expected);
    }

    @Test
    void sameLetterMisplacedAndUnknown() {
      givenGame("ACDC");

      // @formatter:off
      Attempt expected = new AttemptBuilder()
        .withWellPlaced('A')
        .withMisPlaced('D')
        .withUnknown('E')
        .withUnknown('D')
        .build();
      // @formatter:on

      whenGuess("ADED");

      expectAttempt(expected);
    }

    @Test
    void sameLetterWellPlacedAndUnknown() {
      givenGame("ACDC");

      // @formatter:off
      Attempt expected = new AttemptBuilder()
        .withWellPlaced('A')
        .withUnknown('D')
        .withWellPlaced('D')
        .withWellPlaced('C')
        .build();
      // @formatter:on

      whenGuess("ADDC");

      expectAttempt(expected);
    }

    @Test
    void winGame() {
      givenGame("HELLO");

      whenGuess("HELLO");

      expectWin(true);
    }
  }

  @Nested
  class Error {

    @Test
    void notSameLength() {
      givenGame("HELLO");

      whenGuess("HELLOS");

      expectError(new NotSameLengthError(5, 6));
    }
  }

  private void givenGame(String secretWord) {
    game = new Game(new SecretWord(secretWord));
  }

  private void whenGuess(String guess) {
    result = game.guess(new Guess(guess));
  }

  private void expectAttempt(Attempt expected) {
    assertThat(result.getValue()).contains(expected);
  }

  private void expectWin(boolean winState) {
    assertThat(game.win()).isEqualTo(winState);
  }

  private void expectError(GameError error) {
    assertThat(result.getErrors()).contains(List.of(error));
  }
}
