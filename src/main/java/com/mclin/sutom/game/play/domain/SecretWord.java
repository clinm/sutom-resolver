package com.mclin.sutom.game.play.domain;

import java.util.stream.Stream;

public record SecretWord(String secretWord) {
  public char firstLetter() {
    return secretWord.charAt(0);
  }

  public Stream<String> stream() {
    return Stream.of(secretWord.split(""));
  }
}
