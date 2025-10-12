package com.mclin.sutom.game.play.domain;

import com.mclin.sutom.shared.error.domain.Assert;

public class GameCreator {

  private SecretWordRepository secretWordRepository;

  public GameCreator(SecretWordRepository secretWordRepository) {
    Assert.notNull("SecretWordRepository", secretWordRepository);

    this.secretWordRepository = secretWordRepository;
  }

  public Game create() {
    return new Game(secretWordRepository.get());
  }
}
