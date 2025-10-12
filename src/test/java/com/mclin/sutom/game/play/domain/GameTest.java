package com.mclin.sutom.game.play.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mclin.sutom.UnitTest;
import com.mclin.sutom.game.play.domain.Attempt.AttemptBuilder;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@UnitTest
class GameTest {

  @Nested
  class CreateHint {

    @Test
    void initialHint() {
      Game g = game("HI");

      List<Letter> hintLetters = Arrays.asList(Letter.wellPlaced('H'), Letter.DOT_UNKNOWN);
      assertThat(g.hint()).isEqualTo(new Hint(hintLetters));
    }

    @Test
    void afterOneGuessWithWellPlaced() {
      Game g = game("BYE");

      g.guess(new Guess("BYR"));

      List<Letter> hintLetters = Arrays.asList(Letter.wellPlaced('B'), Letter.wellPlaced('Y'), Letter.DOT_UNKNOWN);
      assertThat(g.hint()).isEqualTo(new Hint(hintLetters));
    }
  }

  @Nested
  class GuessWord {

    @Test
    void newGameIsNotWin() {
      Game g = game("HI");

      assertThat(g.win()).isFalse();
    }

    @Test
    void incorretAttemptIsNotWin() {
      Game g = game("HI");

      g.guess(new Guess("HA"));

      assertThat(g.win()).isFalse();
    }

    @Test
    void correctWord() {
      Game g = game("HI");

      // @formatter:off
      Attempt expected = new AttemptBuilder()
        .withWellPlaced('H')
        .withWellPlaced('I')
        .build();
      // @formatter:on
      assertThat(g.guess(new Guess("HI"))).isEqualTo(expected);
    }

    @Test
    void unknownLetter() {
      Game g = game("HI");

      // @formatter:off
      Attempt expected = new AttemptBuilder()
        .withWellPlaced('H')
        .withUnknown('A')
        .build();
      // @formatter:on

      assertThat(g.guess(new Guess("HA"))).isEqualTo(expected);
    }

    @Test
    void wrongPlacedLetter() {
      Game g = game("HI");

      // @formatter:off
      Attempt expected = new AttemptBuilder()
        .withMisPlaced('I')
        .withMisPlaced('H')
        .build();
      // @formatter:on

      assertThat(g.guess(new Guess("IH"))).isEqualTo(expected);
    }

    @Test
    void sameLetterMisplacedAndUnknown() {
      Game g = game("ACDC");

      // @formatter:off
      Attempt expected = new AttemptBuilder()
        .withWellPlaced('A')
        .withMisPlaced('D')
        .withUnknown('E')
        .withUnknown('D')
        .build();
      // @formatter:on

      assertThat(g.guess(new Guess("ADED"))).isEqualTo(expected);
    }

    @Test
    void sameLetterWellPlacedAndUnknown() {
      Game g = game("ACDC");

      // @formatter:off
      Attempt expected = new AttemptBuilder()
        .withWellPlaced('A')
        .withUnknown('D')
        .withWellPlaced('D')
        .withWellPlaced('C')
        .build();
      // @formatter:on

      assertThat(g.guess(new Guess("ADDC"))).isEqualTo(expected);
    }

    @Test
    void winGame() {
      Game g = game("HELLO");

      g.guess(new Guess("HELLO"));

      assertThat(g.win()).isTrue();
    }
  }

  private Game game(String secretWord) {
    return new Game(new SecretWord(secretWord));
  }
}
