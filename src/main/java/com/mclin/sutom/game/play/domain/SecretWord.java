package com.mclin.sutom.game.play.domain;

import java.util.stream.Stream;

public record SecretWord(String secretWord) {
  public char firstLetter() {
    return secretWord.charAt(0);
  }

  public Stream<Character> stream() {
    return secretWord.chars().mapToObj(i -> (char) i);
  }

  public boolean hasCharacterAtPos(Character character, int i) {
    return character.equals(secretWord.charAt(i));
  }

  public boolean contains(Character at) {
    return secretWord.contains(at.toString());
  }
}
