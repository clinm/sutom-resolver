package com.mclin.sutom.game.play.domain;

import com.mclin.sutom.game.play.domain.solver.Word;
import java.util.List;

public interface DictionnaryRepository {
  boolean contains(String word);

  List<Word> wordsStartingWith(char letter);
}
