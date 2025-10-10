package com.mclin.sutom.game.play.domain;

import java.util.Map;

public class LetterStats {

  private Map<Character, Long> stats;

  public LetterStats(Map<Character, Long> stats) {
    this.stats = stats;
  }

  public void decrease(Character current) {
    long remainingCount = stats.get(current) - 1;
    if (remainingCount > 0) {
      stats.put(current, remainingCount);
    } else {
      stats.remove(current);
    }
  }

  public boolean containsKey(Character current) {
    return stats.containsKey(current);
  }
}
