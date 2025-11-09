package com.mclin.sutom.game.play.domain;

import static org.junit.jupiter.api.Assertions.fail;

import com.mclin.sutom.game.play.domain.solver.Word;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class FakeDictionnaryRepository implements DictionnaryRepository {

  private String expectedWord;

  private Map<Character, List<Word>> wordsStartingWith = new HashMap<>();

  public void knows(String word) {
    expectedWord = word;
  }

  public void knowsWordsStartingWith(char letter, Word... words) {
    wordsStartingWith.put(letter, Arrays.asList(words));
  }

  @Override
  public boolean contains(String word) {
    return Objects.equals(expectedWord, word);
  }

  @Override
  public List<Word> wordsStartingWith(char letter) {
    if (!wordsStartingWith.containsKey(letter)) {
      fail("No word starting with " + letter);
    }
    return wordsStartingWith.get(letter);
  }
}
