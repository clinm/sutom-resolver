package com.mclin.sutom.game.play.domain.solver;

import com.mclin.sutom.game.play.domain.DictionnaryRepository;
import com.mclin.sutom.game.play.domain.Game;
import com.mclin.sutom.game.play.domain.Letter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class KnownLetterSolver implements Solver {

  private DictionnaryRepository dictionnary;

  public KnownLetterSolver(DictionnaryRepository dictionnary) {
    this.dictionnary = dictionnary;
  }

  @Override
  public Optional<Word> suggestWord(Game game) {
    var letters = game.hint().letters();
    char firstLetter = letters.getFirst().value();

    HashMap<Integer, Letter> letterMap = wellPlacedLetters(game, letters);
    Set<Word> triedWords = new HashSet<>(game.attempts().words());

    return dictionnary
      .wordsStartingWith(firstLetter)
      .stream()
      .filter(w -> !triedWords.contains(w))
      .filter(w -> matchWellPlacedLetters(letterMap, w))
      .findFirst();
  }

  private HashMap<Integer, Letter> wellPlacedLetters(Game game, List<Letter> letters) {
    HashMap<Integer, Letter> letterMap = new HashMap<>();
    for (int i = 0; i < game.hint().letters().size(); i++) {
      Letter letter = letters.get(i);
      if (letter.isWellPlaced()) {
        letterMap.put(i, letter);
      }
    }
    return letterMap;
  }

  private boolean matchWellPlacedLetters(HashMap<Integer, Letter> letterMap, Word w) {
    return letterMap
      .entrySet()
      .stream()
      .allMatch(entry -> w.value().charAt(entry.getKey()) == entry.getValue().value());
  }
}
