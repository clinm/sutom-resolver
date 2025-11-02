package com.mclin.sutom.game.play.domain;

import com.mclin.sutom.game.play.domain.error.AttemptLimitReachedException;
import com.mclin.sutom.shared.error.domain.Assert;
import java.util.ArrayList;
import java.util.List;

public record Attempts(List<Attempt> attempts) {
  public static final Attempts EMPTY = new Attempts(List.of());

  private static final int MAX_ATTEMPTS = 6;

  public Attempts {
    Assert.notNull("attempts", attempts);
  }

  public Attempts add(Attempt attempt) throws AttemptLimitReachedException {
    if (maxAttemptsReached()) {
      throw new AttemptLimitReachedException();
    }
    List<Attempt> tmp = new ArrayList<>(attempts);
    tmp.add(attempt);
    return new Attempts(List.copyOf(tmp));
  }

  public State state() {
    if (maxAttemptsReached()) {
      return State.LOSE;
    }

    if (attempts.isEmpty()) {
      return State.IN_PROGRESS;
    }
    return attempts.getLast().win();
  }

  private boolean maxAttemptsReached() {
    return attempts.size() == MAX_ATTEMPTS;
  }
}
