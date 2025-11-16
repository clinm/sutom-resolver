package com.mclin.sutom.game.play.domain.solver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.mclin.sutom.game.play.domain.FakeDictionnaryRepository;
import com.mclin.sutom.game.play.domain.Game;
import com.mclin.sutom.game.play.domain.Guess;
import com.mclin.sutom.game.play.domain.SecretWord;
import java.util.Arrays;
import java.util.Optional;

public abstract class SolverHarness {

  private FakeDictionnaryRepository dictionnary = new FakeDictionnaryRepository();

  private Game game;

  private Optional<Word> result;

  protected void givenGame(String secretWord) {
    game = new Game(getDictionnary(), new SecretWord(secretWord));
  }

  public FakeDictionnaryRepository getDictionnary() {
    return dictionnary;
  }

  protected void givenGuess(String guess) {
    assertDoesNotThrow(() -> {
      game.guess(new Guess(guess));
    });
  }

  protected void givenKnowsWords(String... words) {
    var tmp = Arrays.asList(words).stream().map(Word::new).toArray(Word[]::new);
    getDictionnary().knowsWordsStartingWith('H', tmp);
  }

  protected void whenGetSuggestion() {
    Solver solver = givenSolver();
    result = solver.suggestWord(game);
  }

  protected abstract Solver givenSolver();

  protected void expectSuggestionToBe(String suggestion) {
    assertThat(result).contains(new Word(suggestion));
  }

  protected void expectSuggestionToBeEmpty() {
    assertThat(result).isEmpty();
  }
}
