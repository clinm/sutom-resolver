package com.mclin.sutom.game.play.primary.cli;

import com.mclin.sutom.game.play.domain.Attempt;
import com.mclin.sutom.game.play.domain.Game;
import com.mclin.sutom.game.play.domain.Guess;
import com.mclin.sutom.game.play.domain.SecretWord;
import java.util.Scanner;

final class CLI {

  private CLI() {}

  public static void main(String[] args) {
    Game g = new Game(new SecretWord("SECOURS"));
    try (Scanner scanner = new Scanner(System.in)) {
      do {
        System.out.println(new CLIHint(g.hint()));
        String guess = scanner.nextLine();
        Attempt attempt = g.guess(new Guess(guess));
        System.out.println(new CLIAttempt(attempt));
      } while (!g.win());
    }
  }
}
