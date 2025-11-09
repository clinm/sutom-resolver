package com.mclin.sutom.game.play.domain;

import com.mclin.sutom.game.play.domain.solver.Word;
import java.util.List;

public interface DictionnaryRepository {
  boolean contains(Word word);

  List<Word> wordsStartingWith(char letter);
}
