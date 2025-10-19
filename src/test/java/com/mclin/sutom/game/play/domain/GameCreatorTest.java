package com.mclin.sutom.game.play.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.mclin.sutom.UnitTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@UnitTest
@ExtendWith(MockitoExtension.class)
class GameCreatorTest {

  @Mock
  private SecretWordRepository secretWordRepository;

  @Test
  void create() {
    when(secretWordRepository.get()).thenReturn(new SecretWord("HI"));
    GameCreator gc = new GameCreator(secretWordRepository, new FakeDictionnaryRepository());

    Game game = gc.create();

    assertThat(game).isNotNull();

    verify(secretWordRepository).get();
  }
}
