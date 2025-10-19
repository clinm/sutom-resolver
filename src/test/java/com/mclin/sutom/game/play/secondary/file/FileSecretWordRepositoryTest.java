package com.mclin.sutom.game.play.secondary.file;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.mclin.sutom.UnitTest;
import com.mclin.sutom.game.play.domain.SecretWord;
import java.util.Random;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@UnitTest
@ExtendWith(MockitoExtension.class)
class FileSecretWordRepositoryTest {

  private FileSecretWordRepository repository;

  @Mock
  private Random random;

  @Test
  void loadSingleSecret() {
    givenSecretWordFile("./src/test/resources/secrets/single-secret-word.txt");
    givenRandom(1, 0);

    SecretWord secretWord = repository.get();

    assertThat(secretWord).isEqualTo(new SecretWord("SECRET"));
  }

  @Test
  void loadMultipleSecret() {
    givenSecretWordFile("./src/test/resources/secrets/multiple-secret-word.txt");
    givenRandom(20, 6);

    SecretWord secretWord = repository.get();

    assertThat(secretWord).isEqualTo(new SecretWord("SECRET7"));
  }

  @Test
  void cannotLoadFile() {
    givenSecretWordFile("./src/test/resources/secrets/cannot-load-file.txt");

    Exception exception = assertThrows(RuntimeException.class, () -> repository.get());

    assertThat(exception).isInstanceOf(RuntimeException.class);
  }

  private void givenRandom(int size, int randomValue) {
    when(random.nextInt(0, size)).thenReturn(randomValue);
  }

  private void givenSecretWordFile(String filePath) {
    repository = new FileSecretWordRepository(filePath, random);
  }
}
