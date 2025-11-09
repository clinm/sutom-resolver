package com.mclin.sutom.game.play.domain.solver;

import com.mclin.sutom.game.play.domain.Game;
import java.util.Optional;

public interface Solver {
  Optional<Word> suggestWord(Game game);
}
