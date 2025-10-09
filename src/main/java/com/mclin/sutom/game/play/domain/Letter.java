package com.mclin.sutom.game.play.domain;

public record Letter(char value) {
  public static final Letter UNKNOWN = new Letter('.');
}
