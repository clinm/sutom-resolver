package com.mclin.sutom.game.play.domain;

public record Guess(String value) {
  public Character at(int i) {
    return value.charAt(i);
  }

  public int length() {
    return value.length();
  }
}
