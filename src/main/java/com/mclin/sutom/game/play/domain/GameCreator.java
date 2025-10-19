package com.mclin.sutom.game.play.domain;

import com.mclin.sutom.shared.error.domain.Assert;

public class GameCreator {

  private SecretWordRepository secretWordRepository;
  private DictionnaryRepository dictionnary;

  public GameCreator(SecretWordRepository secretWordRepository, DictionnaryRepository dictionnary) {
    Assert.notNull("SecretWordRepository", secretWordRepository);
    Assert.notNull("DictionnaryRepository", dictionnary);

    this.secretWordRepository = secretWordRepository;
    this.dictionnary = dictionnary;
  }

  public Game create() {
    return new Game(dictionnary, secretWordRepository.get());
  }
}
