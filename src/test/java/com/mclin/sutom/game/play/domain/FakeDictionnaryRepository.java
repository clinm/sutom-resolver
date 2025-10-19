package com.mclin.sutom.game.play.domain;

import java.util.Objects;

public class FakeDictionnaryRepository implements DictionnaryRepository {

  private String expectedWord;

  public void knows(String word) {
    expectedWord = word;
  }

  @Override
  public boolean contains(String word) {
    return Objects.equals(expectedWord, word);
  }
}
