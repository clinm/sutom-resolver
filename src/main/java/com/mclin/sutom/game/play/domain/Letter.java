package com.mclin.sutom.game.play.domain;

public record Letter(char value, State state) {
  public static final Letter DOT_UNKNOWN = new Letter('.');

  public Letter(char value) {
    this(value, State.UNKNOWN);
  }

  public boolean isWellPlaced() {
    return state == State.WELL_PLACED;
  }

  public static Letter unknown(char value) {
    return new Letter(value, State.UNKNOWN);
  }

  public static Letter wellPlaced(char value) {
    return new Letter(value, State.WELL_PLACED);
  }

  public static Letter misplaced(char value) {
    return new Letter(value, State.MISPLACED);
  }
}
