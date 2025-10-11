package com.mclin.sutom.game.play.primary.cli;

import com.mclin.sutom.game.play.domain.Hint;

record CLIHint(Hint hint) {
  @Override
  public String toString() {
    return hint.letters().stream().map(CLILetter::new).collect(StringBuilder::new, StringBuilder::append, StringBuilder::append).toString();
  }
}
