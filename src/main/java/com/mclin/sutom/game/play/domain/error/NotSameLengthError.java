package com.mclin.sutom.game.play.domain.error;

public record NotSameLengthError(int expectedLength, int actualLength) implements GameError {}
