package com.mclin.sutom.game.play.primary.cli;

enum Color {
  //Color end string, color reset
  RESET("\033[0m"),

  // Regular Colors. Normal color, no bold, background color etc.
  BLACK("\033[0;30m"),
  RED("\033[0;31m"),
  YELLOW("\033[0;33m");

  private final String code;

  Color(String code) {
    this.code = code;
  }

  @Override
  public String toString() {
    return code;
  }
}
