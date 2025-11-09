package com.mclin.sutom.game.play.domain;

import static org.junit.jupiter.api.Assertions.fail;

import com.mclin.sutom.game.play.domain.solver.Word;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FakeDictionnaryRepository implements DictionnaryRepository {

  private Map<Character, List<Word>> wordsStartingWith = new HashMap<>();

  public void knows(String word) {
    wordsStartingWith.put(word.charAt(0), List.of(new Word(word)));
  }

  public void knowsWordsStartingWith(char letter, Word... words) {
    wordsStartingWith.put(letter, Arrays.asList(words));
  }

  @Override
  public boolean contains(Word word) {
    return wordsStartingWith.getOrDefault(word.value().charAt(0), new ArrayList<Word>()).contains(word);
  }

  @Override
  public List<Word> wordsStartingWith(char letter) {
    if (!wordsStartingWith.containsKey(letter)) {
      fail("No word starting with " + letter);
    }
    return wordsStartingWith.get(letter);
  }
}
