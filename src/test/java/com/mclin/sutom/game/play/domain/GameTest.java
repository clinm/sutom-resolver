package com.mclin.sutom.game.play.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mclin.sutom.UnitTest;
import com.mclin.sutom.game.play.domain.Attempt.AttemptBuilder;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GameTest {

  @Test
  void initialHint() {
    Game g = game();

    List<Letter> hintLetters = Arrays.asList(new Letter('H'), Letter.DOT_UNKNOWN);
    assertThat(g.hint()).isEqualTo(new Hint(hintLetters));
  }

  @Test
  void guessCorrectWord() {
    Game g = game();

    Attempt expected = new AttemptBuilder().withWellPlaced('H').withWellPlaced('I').build();
    assertThat(g.guess(new Guess("HI"))).isEqualTo(expected);
  }

  @Test
  void guessUnknownLetter() {
    Game g = game();

    Attempt expected = new AttemptBuilder().withWellPlaced('H').withUnknown('A').build();
    assertThat(g.guess(new Guess("HA"))).isEqualTo(expected);
  }

  @Test
  void guessWrongPlacedLetter() {
    Game g = game();

    Attempt expected = new AttemptBuilder().withMisPlaced('I').withMisPlaced('H').build();
    assertThat(g.guess(new Guess("IH"))).isEqualTo(expected);
  }

  private Game game() {
    return new Game(new SecretWord("HI"));
  }
}
