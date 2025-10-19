package com.mclin.sutom.game.play.domain.error;

public record NotInDictionaryError(String word) implements GameError {}
