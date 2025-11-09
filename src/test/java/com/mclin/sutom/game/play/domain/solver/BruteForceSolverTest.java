package com.mclin.sutom.game.play.domain.solver;

import static org.assertj.core.api.Assertions.assertThat;

import com.mclin.sutom.UnitTest;
import com.mclin.sutom.game.play.domain.FakeDictionnaryRepository;
import com.mclin.sutom.game.play.domain.Game;
import com.mclin.sutom.game.play.domain.SecretWord;
import java.util.Arrays;
import java.util.Optional;
import org.junit.jupiter.api.Test;

@UnitTest
class BruteForceSolverTest {

  private FakeDictionnaryRepository dictionnary = new FakeDictionnaryRepository();

  private Game game;
  private Optional<Word> result;

  // TODO first word matching first letter not already tried

  @Test
  void shouldProposeFirstWordMatchingFirstLetter() {
    givenGame();
    givenKnowsWords("HALLO");

    whenGetSuggestion();

    expectSuggestionToBe("HALLO");
  }

  private Game givenGame() {
    return game = new Game(dictionnary, new SecretWord("HELLO"));
  }

  private void givenKnowsWords(String... words) {
    var tmp = Arrays.asList(words).stream().map(Word::new).toArray(Word[]::new);
    dictionnary.knowsWordsStartingWith('H', tmp);
  }

  private void whenGetSuggestion() {
    Solver solver = new BruteForceSolver(dictionnary);
    result = solver.suggestWord(game);
  }

  private void expectSuggestionToBe(String suggestion) {
    assertThat(result).contains(new Word(suggestion));
  }
}
