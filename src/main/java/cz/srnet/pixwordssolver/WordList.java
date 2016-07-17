package cz.srnet.pixwordssolver;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.eclipse.jdt.annotation.NonNullByDefault;

@NonNullByDefault
public class WordList {

	public WordList(Path dir) throws IOException {
		words = new HashSet<>();
		loadWords(words, dir);
	}

	WordList(Set<String> words) {
		this.words = words;
	}

	private final Set<String> words;

	private void loadWords(Set<String> words, Path dir) throws IOException {
		if (Files.isDirectory(dir)) {
			try (DirectoryStream<Path> stream = regularFiles(dir)) {
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

	@SuppressWarnings("null")
	private DirectoryStream<Path> regularFiles(Path dir) throws IOException {
		return Files.newDirectoryStream(dir, file -> Files.isRegularFile(file));
	}

	private void loadWordsFromFile(Set<String> words, Path file) {
		// System.out.println("file=" + file);
		try (Stream<String> lines = fileLines(file)) {
			lines.map(line -> line.trim().toLowerCase()).forEach(word -> words.add(Utils.notNull(word)));
		} catch (IOException e) {
			// Eclipse refuses to compile this with throws IOException
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("null")
	private Stream<String> fileLines(Path file) throws IOException {
		return Files.lines(file, StandardCharsets.ISO_8859_1);
	}

	public boolean isListed(String word) {
		return words.contains(word);
	}

	public Stream<String> list(String pattern) {
		Pattern regex = Pattern.compile(pattern.replace('?', '.'), Pattern.CASE_INSENSITIVE);
		return Utils.notNull(words.stream().filter(word -> regex.matcher(word).matches()));
	}
}
