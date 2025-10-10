package com.mclin.sutom.game.play.domain;

import java.util.function.Function;
import java.util.stream.Collectors;
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

  private LetterStats stats() {
    var stats = stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    return new LetterStats(stats);
  }

  public LetterStats misplacedLettersStats(Guess guess) {
    var stats = stats();

    for (int i = 0; i < guess.value().length(); i++) {
      Character current = guess.at(i);
      if (hasCharacterAtPos(current, i)) {
        stats.decrease(current);
      }
    }

    return stats;
  }
}
