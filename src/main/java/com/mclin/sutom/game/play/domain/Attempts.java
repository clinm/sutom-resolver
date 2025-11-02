package com.mclin.sutom.game.play.domain;

import com.mclin.sutom.shared.error.domain.Assert;
import java.util.ArrayList;
import java.util.List;

public record Attempts(List<Attempt> attempts) {
  public static final Attempts EMPTY = new Attempts(List.of());

  public Attempts {
    Assert.notNull("attempts", attempts);
  }

  public Attempts add(Attempt attempt) {
    List<Attempt> tmp = new ArrayList<>(attempts);
    tmp.add(attempt);
    return new Attempts(List.copyOf(tmp));
  }

  public State state() {
    if (attempts.isEmpty()) {
      return State.IN_PROGRESS;
    }
    return attempts.getLast().win();
  }
}
