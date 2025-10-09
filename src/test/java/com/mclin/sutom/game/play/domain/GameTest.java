package com.mclin.sutom.game.play.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

class GameTest {

  @Test
  void initialHint() {
    Game g = new Game(new SecretWord("HI"));

    List<Letter> hintLetters = Arrays.asList(new Letter('H'), Letter.UNKNOWN);
    assertThat(g.hint()).isEqualTo(new Hint(hintLetters));
  }
}
