package com.mclin.sutom.game.play.primary.cli;

import com.mclin.sutom.game.play.domain.Attempt;

record CLIAttempt(Attempt attempt) {
  @Override
  public String toString() {
    return attempt
      .letters()
      .stream()
      .map(CLILetter::new)
      .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
      .toString();
  }
}
