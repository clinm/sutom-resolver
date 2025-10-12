package com.mclin.sutom.game.play.secondary.memory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.mclin.sutom.UnitTest;
import com.mclin.sutom.game.play.domain.SecretWord;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@UnitTest
@ExtendWith(MockitoExtension.class)
class InMemorySecretWordRepositoryTest {

  @Mock
  private Random random;

  @Test
  void get() {
    var words = List.of(new SecretWord("HI"), new SecretWord("HELLO"));
    when(random.nextInt(0, words.size())).thenReturn(1);
    InMemorySecretWordRepository repository = new InMemorySecretWordRepository(words, random);

    SecretWord secretWord = repository.get();

    assertThat(secretWord.secretWord()).isEqualTo("HELLO");
  }
}
