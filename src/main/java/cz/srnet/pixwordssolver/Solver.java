package cz.srnet.pixwordssolver;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Solver {

	public Solver(String pattern, String letters) {
		this.pattern = pattern;
		this.letters = letters;
		this.estimatedPossibilities = estimatePossibilities();
	}

	private final String pattern;
	private final String letters;
	private final int estimatedPossibilities;

	public int getEstimatedPossibilities() {
		return estimatedPossibilities;
	}

	private int estimatePossibilities() {
		int unknown = 0;
		for (int i = 0; i < pattern.length(); i++) {
			if (pattern.charAt(i) == '?') {
				unknown++;
			}
		}
		int estimate = 1;
		for (int n = letters.length(); n > letters.length() - unknown; n--) {
			estimate *= n;
		}
		return estimate;
	}

	public Collection<String> solve() {
		List<Character> lettersAsList = new ArrayList<>();
		if (!letters.isEmpty()) {
			for (String letter : letters.split("")) {
				lettersAsList.add(letter.charAt(0));
			}
		}
		return solve("", pattern, lettersAsList);
	}

	private static Collection<String> solve(String prefix, String pattern,
			Collection<Character> letters) {
		if (pattern.isEmpty()) {
			return Collections.singleton(prefix);
		}
		Collection<String> ret = new ArrayList<>();
		char c = pattern.charAt(0);
		String restOfPattern = pattern.substring(1);
		if (c == '?') {
			Set<Character> lettersAsSet = new LinkedHashSet<>(letters);
			for (char letter : lettersAsSet) {
				List<Character> restOfLetters = new ArrayList<>(letters);
				restOfLetters.remove(new Character(letter));
				ret.addAll(solve(prefix + letter, restOfPattern, restOfLetters));
			}
		} else {
			ret.addAll(solve(prefix + c, restOfPattern, letters));
		}
		return ret;
	}

	public static void main(String[] args) throws IOException {
		String pattern = args[0];
		String letters = args[1];
		WordList wordList = null;
		if (args.length > 2) {
			String wordListsDir = args[2];
			Path wordListsDirPath = FileSystems.getDefault().getPath(
					wordListsDir);
			System.out.println("Loading word list from " + wordListsDirPath);
			wordList = new WordList(wordListsDirPath);
			System.out.println("... loaded");
		}

		System.out.println("Generating possibilities...");
		Solver solver = new Solver(pattern, letters);
		System.out.println("... " + solver.getEstimatedPossibilities()
				+ " estimated possibilities");
		Collection<String> possibilities = solver.solve();
		System.out.println("... generated");

		System.out.println("Possibilities:");
		System.out.println(possibilities.size());
		possibilities.forEach(possibility -> System.out.println(possibility));
		System.out.println();

		if (wordList != null) {
			System.out.println("Possible words: ");
			final WordList wordList_f = wordList;
			possibilities.stream()
					.filter(possibility -> wordList_f.isListed(possibility))
					.forEach(word -> System.out.println(word));
		}
	}
}
