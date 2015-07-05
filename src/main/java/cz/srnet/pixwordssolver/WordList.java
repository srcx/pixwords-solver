package cz.srnet.pixwordssolver;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

public class WordList {

	public WordList(Path dir) throws IOException {
		loadWords(words, dir);
	}

	private final Set<String> words = new HashSet<>();

	private void loadWords(Set<String> words, Path dir) throws IOException {
		if (Files.isDirectory(dir)) {
			try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir,
					file -> Files.isRegularFile(file))) {
				stream.forEach(file -> loadWordsFromFile(words, file));
			} catch (RuntimeException e) {
				if (e.getCause() instanceof IOException) {
					throw (IOException) e.getCause();
				}
				throw e;
			}
		} else {
			loadWordsFromFile(words, dir);
		}
	}

	private void loadWordsFromFile(Set<String> words, Path file) {
		// System.out.println("file=" + file);
		try (Stream<String> lines = Files.lines(file,
				StandardCharsets.ISO_8859_1)) {
			lines.map(line -> line.trim()).forEach(word -> words.add(word));
		} catch (IOException e) {
			// Eclipse refuses to compile this with throws IOException
			throw new RuntimeException(e);
		}
	}

	public boolean isListed(String word) {
		return words.contains(word);
	}

}
