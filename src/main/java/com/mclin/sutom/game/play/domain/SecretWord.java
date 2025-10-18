package com.mclin.sutom.game.play.domain;

import com.mclin.sutom.game.play.domain.Attempt.AttemptBuilder;
import com.mclin.sutom.shared.error.domain.Assert;
import java.util.ArrayList;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public record SecretWord(String secretWord) {
  public SecretWord {
    Assert.field("secretWord", secretWord).notBlank();
  }

  public Hint initialHint() {
    Letter firstLetter = Letter.wellPlaced(firstLetter());
    var letters = new ArrayList<Letter>();
    letters.add(firstLetter);

    stream()
      .skip(1)
      .map(l -> Letter.DOT_UNKNOWN)
      .forEach(letters::add);

    return new Hint(letters);
  }

  public Attempt guess(Guess guess) {
    var builder = new AttemptBuilder();

    LetterStats stats = misplacedLettersStats(guess);

    for (int i = 0; i < guess.value().length(); i++) {
      Character current = guess.at(i);
      if (hasCharacterAtPos(current, i)) {
        builder.withWellPlaced(current);
      } else if (stats.containsKey(current)) {
        builder.withMisPlaced(current);
        stats.decrease(current);
      } else {
        builder.withUnknown(current);
      }
    }

    return builder.build();
  }

  private char firstLetter() {
    return secretWord.charAt(0);
  }

  private Stream<Character> stream() {
    return secretWord.chars().mapToObj(i -> (char) i);
  }

  private boolean hasCharacterAtPos(Character character, int i) {
    return character.equals(secretWord.charAt(i));
  }

  private LetterStats stats() {
    var stats = stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    return new LetterStats(stats);
  }

  private LetterStats misplacedLettersStats(Guess guess) {
    var stats = stats();

    for (int i = 0; i < guess.value().length(); i++) {
      Character current = guess.at(i);
      if (hasCharacterAtPos(current, i)) {
        stats.decrease(current);
      }
    }

    return stats;
  }

  public int length() {
    return secretWord.length();
  }
}
