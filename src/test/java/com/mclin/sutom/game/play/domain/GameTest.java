package com.mclin.sutom.game.play.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.mclin.sutom.UnitTest;
import com.mclin.sutom.game.play.domain.Attempt.AttemptBuilder;
import com.mclin.sutom.game.play.domain.error.AttemptLimitReachedException;
import com.mclin.sutom.game.play.domain.error.GameError;
import com.mclin.sutom.game.play.domain.error.NotInDictionaryError;
import com.mclin.sutom.game.play.domain.error.NotSameLengthError;
import com.mclin.sutom.shared.result.domain.Result;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@UnitTest
class GameTest {

  private Game game;

  private Result<Attempt, GameError> result;

  private FakeDictionnaryRepository dictionnary = new FakeDictionnaryRepository();

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
      givenKnownWord("BYR");

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

      expectInProgress();
    }

    @Test
    void incorrectAttemptIsNotWin() {
      givenGame("HI");
      givenKnownWord("HA");

      whenGuess("HA");

      expectInProgress();
    }

    @Test
    void correctWord() {
      givenGame("HI");
      givenKnownWord("HI");

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
      givenKnownWord("HA");

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
      givenKnownWord("IH");

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
      givenKnownWord("ADED");

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
      givenKnownWord("ADDC");

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
      givenKnownWord("HELLO");

      whenGuess("HELLO");

      expectWin();
    }

    @Test
    void loseGame() {
      givenGame("HELLO");
      givenKnownWord("HALLO");

      whenLoseWithWord("HALLO");

      expectLose();
    }
  }

  @Nested
  class Error {

    @Test
    void notSameLength() {
      givenGame("HELLO");
      givenKnownWord("HELLOS");

      whenGuess("HELLOS");

      expectError(new NotSameLengthError(5, 6));
    }

    @Test
    void guessNotInDictionary() {
      givenGame("HELLO");

      whenGuess("HALLO");
      expectError(new NotInDictionaryError("HALLO"));
    }

    @Test
    void guessAfterLose() {
      givenGame("HELLO");
      givenKnownWord("HALLO");

      whenLoseWithWord("HALLO");

      expectThrowsOnNewGuess("HALLO");
    }

    private AttemptLimitReachedException expectThrowsOnNewGuess(String guess) {
      return assertThrows(AttemptLimitReachedException.class, () -> game.guess(new Guess(guess)));
    }
  }

  private void givenGame(String secretWord) {
    game = new Game(dictionnary, new SecretWord(secretWord));
  }

  private void whenGuess(String guess) {
    assertDoesNotThrow(() -> {
      result = game.guess(new Guess(guess));
    });
  }

  private void whenLoseWithWord(String guess) {
    whenGuess(guess);
    whenGuess(guess);
    whenGuess(guess);
    whenGuess(guess);
    whenGuess(guess);
    whenGuess(guess);
  }

  private void givenKnownWord(String word) {
    dictionnary.knows(word);
  }

  private void expectAttempt(Attempt expected) {
    assertThat(result.getValue()).contains(expected);
  }

  private void expectWin() {
    assertThat(game.state()).isEqualTo(State.WIN);
  }

  private void expectInProgress() {
    assertThat(game.state()).isEqualTo(State.IN_PROGRESS);
  }

  public void expectLose() {
    assertThat(game.state()).isEqualTo(State.LOSE);
  }

  private void expectError(GameError error) {
    assertThat(result.getErrors()).contains(List.of(error));
  }
}
