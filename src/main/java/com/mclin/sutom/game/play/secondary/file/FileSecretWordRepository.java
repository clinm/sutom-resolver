package com.mclin.sutom.game.play.secondary.file;

import com.mclin.sutom.game.play.domain.SecretWord;
import com.mclin.sutom.game.play.domain.SecretWordRepository;
import com.mclin.sutom.shared.error.domain.Assert;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

public class FileSecretWordRepository implements SecretWordRepository {

  private String filePath;
  private Random random;

  public FileSecretWordRepository(String filePath, Random random) {
    Assert.notNull("filePath", filePath);
    Assert.notNull("random", random);

    this.filePath = filePath;
    this.random = random;
  }

  @Override
  public SecretWord get() {
    Path p = Paths.get(filePath);

    try {
      List<String> lines = Files.readAllLines(p);
      return new SecretWord(lines.get(random.nextInt(0, lines.size())));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
