package com.mclin.sutom.game.play.primary.cli;

import static org.assertj.core.api.Assertions.assertThat;

import com.mclin.sutom.UnitTest;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.Test;

@UnitTest
class CLITest {

  @Test
  void fullGame() {
    String input = "SOURIRE" + System.lineSeparator() + "SECOURS";
    System.setIn(new java.io.ByteArrayInputStream(input.getBytes()));

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(baos);
    System.setOut(printStream);

    CLI.main(null);

    assertThat(baos.toString()).hasToString(expectedFullOutput());
  }

  String expectedFullOutput() {
    return (
      "[0;31mS[0m......"
      + System.lineSeparator()
      + "[0;31mS[0m[0;33mO[0m[0;33mU[0mRI[0;31mR[0m[0;33mE[0m"
      + System.lineSeparator()
      + "[0;31mS[0m....[0;31mR[0m."
      + System.lineSeparator()
      + "[0;31mS[0m[0;31mE[0m[0;31mC[0m[0;31mO[0m[0;31mU[0m[0;31mR[0m[0;31mS[0m"
      + System.lineSeparator()
    );
  }
}
