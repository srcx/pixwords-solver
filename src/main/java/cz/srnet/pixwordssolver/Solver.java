package cz.srnet.pixwordssolver;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

import org.eclipse.jdt.annotation.NonNullByDefault;

@NonNullByDefault
public class Solver {

	private static final int POSSIBILITIES_LIMIT = 1000;

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
		Collection<String> ret = new ArrayList<String>();
		solve(possibility -> ret.add(possibility));
		return ret;
	}

	public long solve(Consumer<String> callback) {
		List<Character> lettersAsList = lettersAsList();
		return solve(callback, "", pattern, lettersAsList);
	}

	private List<Character> lettersAsList() {
		List<Character> lettersAsList = new ArrayList<>();
		if (!letters.isEmpty()) {
			for (String letter : letters.split("")) {
				lettersAsList.add(Utils.notNull(letter.charAt(0)));
			}
		}
		return lettersAsList;
	}

	private static long solve(Consumer<String> callback, String prefix, String pattern, Collection<Character> letters) {
		if (pattern.isEmpty()) {
			callback.accept(prefix);
			return 1;
		}
		long ret = 0;
		char c = pattern.charAt(0);
		String restOfPattern = Utils.notNull(pattern.substring(1));
		if (c == '?') {
			Set<Character> lettersAsSet = new LinkedHashSet<>(letters);
			for (char letter : lettersAsSet) {
				List<Character> restOfLetters = new ArrayList<>(letters);
				restOfLetters.remove(Character.valueOf(letter));
				ret += solve(callback, prefix + letter, restOfPattern, restOfLetters);
			}
		} else {
			ret += solve(callback, prefix + c, restOfPattern, letters);
		}
		return ret;
	}

	public boolean matches(String possibleWord) {
		if (possibleWord.length() != pattern.length()) {
			return false;
		}
		List<Character> lettersAsList = lettersAsList();
		for (int i = 0; i < possibleWord.length(); i++) {
			char wordChar = possibleWord.charAt(i);
			char patternChar = pattern.charAt(i);
			if (patternChar == '?') {
				if (!lettersAsList.remove(Character.valueOf(wordChar))) {
					return false;
				}
			} else if (wordChar != patternChar) {
				return false;
			}
		}
		return true;
	}

	public static void main(String[] args) throws IOException {
		String pattern = Utils.notNull(args[0]);
		String letters = Utils.notNull(args[1]);
		WordList wordList = null;
		if (args.length > 2) {
			String wordListsDir = args[2];
			Path wordListsDirPath = Utils.notNull(FileSystems.getDefault().getPath(wordListsDir));
			System.out.println("Loading word list from " + wordListsDirPath);
			wordList = new WordList(wordListsDirPath);
			System.out.println("... loaded");
		}

		System.out.println("Generating possibilities...");
		Solver solver = new Solver(pattern, letters);
		System.out.println("... " + solver.getEstimatedPossibilities() + " estimated possibilities");
		if (wordList == null || solver.getEstimatedPossibilities() < POSSIBILITIES_LIMIT) {
			System.out.println("... generating all of them");
			List<String> possibleWords = new ArrayList<String>();
			final WordList wordList_f = wordList;
			long generated = solver.solve(possibility -> {
				System.out.print(possibility);
				if (wordList_f != null && wordList_f.isListed(possibility)) {
					possibleWords.add(possibility);
					System.out.print(" [*]");
				}
				System.out.println();
			});
			System.out.println("... " + generated + " generated possibilities");

			if (wordList != null) {
				System.out.println("Possible words: ");
				possibleWords.stream().forEach(word -> System.out.println(word));
			}
		} else {
			System.out.println("... too many possibilities - using word list directly");
			System.out.println("Possible words: ");
			wordList.list(pattern).filter(word -> solver.matches(word)).forEach(word -> System.out.println(word));
		}
	}
}
