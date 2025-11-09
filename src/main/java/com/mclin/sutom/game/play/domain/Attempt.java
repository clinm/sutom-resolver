package com.mclin.sutom.game.play.domain;

import com.mclin.sutom.game.play.domain.solver.Word;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public record Attempt(List<Letter> letters) {
  public Letter at(int index) {
    return letters.get(index);
  }

  public State win() {
    return letters.stream().allMatch(Letter::isWellPlaced) ? State.WIN : State.IN_PROGRESS;
  }

  public Word word() {
    return new Word(letters.stream().map(Letter::value).map(String::valueOf).collect(Collectors.joining()));
  }

  public static final class AttemptBuilder {

    private List<Letter> letters = new ArrayList<>();

    public AttemptBuilder withUnknown(Character letter) {
      letters.add(Letter.unknown(letter));
      return this;
    }

    public AttemptBuilder withWellPlaced(Character letter) {
      letters.add(Letter.wellPlaced(letter));
      return this;
    }

    public AttemptBuilder withMisPlaced(Character letter) {
      letters.add(Letter.misplaced(letter));
      return this;
    }

    public Attempt build() {
      return new Attempt(letters);
    }
  }
}
