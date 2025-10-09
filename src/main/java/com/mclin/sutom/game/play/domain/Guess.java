package com.mclin.sutom.game.play.domain;

import java.util.stream.Stream;

public record Guess(String value) {
  public Stream<Character> stream() {
    return value.chars().mapToObj(i -> (char) i);
  }
}
