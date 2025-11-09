package com.mclin.sutom.game.play.domain.solver;

import com.mclin.sutom.game.play.domain.DictionnaryRepository;
import com.mclin.sutom.game.play.domain.Game;
import java.util.Optional;

public class BruteForceSolver implements Solver {

  private DictionnaryRepository dictionnary;

  public BruteForceSolver(DictionnaryRepository dictionnary) {
    this.dictionnary = dictionnary;
  }

  @Override
  public Optional<Word> suggestWord(Game game) {
    char firstLetter = game.hint().letters().getFirst().value();

    return dictionnary.wordsStartingWith(firstLetter).stream().findFirst();
  }
}
