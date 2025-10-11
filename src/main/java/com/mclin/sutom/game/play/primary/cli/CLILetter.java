package com.mclin.sutom.game.play.primary.cli;

import com.mclin.sutom.game.play.domain.Letter;

record CLILetter(Letter letter) {
  @Override
  public String toString() {
    String value = String.valueOf(letter.value());

    if (letter.isWellPlaced()) {
      return Color.RED + value + Color.RESET;
    } else if (letter.isMisplaced()) {
      return Color.YELLOW + value + Color.RESET;
    } else {
      return value;
    }
  }
}
