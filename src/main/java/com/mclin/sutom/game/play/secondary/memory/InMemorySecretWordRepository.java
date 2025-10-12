package com.mclin.sutom.game.play.secondary.memory;

import com.mclin.sutom.game.play.domain.SecretWord;
import com.mclin.sutom.game.play.domain.SecretWordRepository;
import com.mclin.sutom.shared.error.domain.Assert;
import java.util.List;
import java.util.Random;

public class InMemorySecretWordRepository implements SecretWordRepository {

  private List<SecretWord> secretWords;
  private Random random;

  public InMemorySecretWordRepository(List<SecretWord> secretWords, Random random) {
    Assert.field("secretWords", secretWords).notEmpty();
    Assert.notNull("random", random);

    this.secretWords = secretWords;
    this.random = random;
  }

  @Override
  public SecretWord get() {
    int index = random.nextInt(0, secretWords.size());
    return secretWords.get(index);
  }
}
