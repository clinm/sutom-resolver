package com.mclin.sutom.game.play.domain.solver;

import com.mclin.sutom.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class BruteForceSolverTest extends SolverHarness {

  @Test
  void shouldProposeFirstWordMatchingFirstLetter() {
    givenGame("HELLO");
    givenKnowsWords("HALLO");

    whenGetSuggestion();

    expectSuggestionToBe("HALLO");
  }

  @Test
  void shouldProposeFirstWordNotAlreadyTried() {
    givenGame("HELLO");
    givenKnowsWords("HALLO", "HALTE");
    givenGuess("HALLO");

    whenGetSuggestion();

    expectSuggestionToBe("HALTE");
  }

  @Test
  void shouldNotProposeWhenNoMoreWords() {
    givenGame("HELLO");
    givenKnowsWords("HALLO");
    givenGuess("HALLO");

    whenGetSuggestion();

    expectSuggestionToBeEmpty();
  }

  @Override
  protected Solver givenSolver() {
    return new BruteForceSolver(getDictionnary());
  }
}
