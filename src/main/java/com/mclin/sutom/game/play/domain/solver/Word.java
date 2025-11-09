package com.mclin.sutom.game.play.domain.solver;

import com.mclin.sutom.shared.error.domain.Assert;

public record Word(String value) {
  public Word {
    Assert.notNull("value", value);
  }
}
