package com.mclin.sutom.game.play.primary.cli;

import com.mclin.sutom.game.play.domain.Attempt;
import com.mclin.sutom.game.play.domain.DictionnaryRepository;
import com.mclin.sutom.game.play.domain.Game;
import com.mclin.sutom.game.play.domain.Guess;
import com.mclin.sutom.game.play.domain.SecretWord;
import com.mclin.sutom.game.play.domain.error.AttemptLimitReachedException;
import com.mclin.sutom.game.play.domain.error.GameError;
import com.mclin.sutom.game.play.domain.solver.Word;
import com.mclin.sutom.shared.result.domain.Result;
import java.util.List;
import java.util.Scanner;

final class CLI {

  private CLI() {}

  public static void main(String[] args) throws AttemptLimitReachedException {
    DictionnaryRepository dictionnary = new YesDictionnaryRepository();
    Game g = new Game(dictionnary, new SecretWord("SECOURS"));
    try (Scanner scanner = new Scanner(System.in)) {
      do {
        System.out.println(new CLIHint(g.hint()));
        String guessInput = scanner.nextLine();
        Result<Attempt, GameError> result = g.guess(new Guess(guessInput));
        Attempt attempt = result.getValue().get();
        System.out.println(new CLIAttempt(attempt));
      } while (g.state().keepPlaying());
    }
  }

  private static class YesDictionnaryRepository implements DictionnaryRepository {

    @Override
    public boolean contains(String word) {
      return true;
    }

    @Override
    public List<Word> wordsStartingWith(char letter) {
      return List.of();
    }
  }
}
