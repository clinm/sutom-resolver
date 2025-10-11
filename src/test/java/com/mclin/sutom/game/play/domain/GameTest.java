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
    void correctWord() {
      Game g = game("HI");

      Attempt expected = new AttemptBuilder().withWellPlaced('H').withWellPlaced('I').build();
      assertThat(g.guess(new Guess("HI"))).isEqualTo(expected);
    }

    @Test
    void unknownLetter() {
      Game g = game("HI");

      Attempt expected = new AttemptBuilder().withWellPlaced('H').withUnknown('A').build();
      assertThat(g.guess(new Guess("HA"))).isEqualTo(expected);
    }

    @Test
    void wrongPlacedLetter() {
      Game g = game("HI");

      Attempt expected = new AttemptBuilder().withMisPlaced('I').withMisPlaced('H').build();
      assertThat(g.guess(new Guess("IH"))).isEqualTo(expected);
    }

    @Test
    void multipleWrongPlacedLetter() {
      Game g = game("HEYLL");

      Attempt expected = new AttemptBuilder()
        .withWellPlaced('H')
        .withWellPlaced('E')
        .withMisPlaced('L')
        .withWellPlaced('L')
        .withUnknown('O')
        .build();

      assertThat(g.guess(new Guess("HELLO"))).isEqualTo(expected);
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
