package com.mclin.sutom.game.play.domain;

import com.mclin.sutom.game.play.domain.solver.Word;

public record Guess(String value) {
  public Character at(int i) {
    return value.charAt(i);
  }

  public int length() {
    return value.length();
  }

  public Word word() {
    return new Word(value);
  }
}
