package com.mclin.sutom.game.play.domain.solver;

import com.mclin.sutom.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class KnownLetterSolverTest extends SolverHarness {

  @Test
  void shouldProposeWordStartingWithLetter() {
    givenGame("HELLO");
    givenKnowsWords("HALLO", "HELLO");

    whenGetSuggestion();

    expectSuggestionToBe("HALLO");
  }

  @Test
  void shouldNotProposeTwiceSameWord() {
    givenGame("HELLO");
    givenKnowsWords("HIGHS", "HELLO");
    givenGuess("HIGHS");

    whenGetSuggestion();

    expectSuggestionToBe("HELLO");
  }

  @Test
  void shouldUseWellPlacedLetters() {
    givenGame("HELLO");
    givenKnowsWords("HEIRS", "HIGHS", "HIKES", "HELLO");
    givenGuess("HEIRS");

    whenGetSuggestion();

    expectSuggestionToBe("HELLO");
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
    return new KnownLetterSolver(getDictionnary());
  }
}
