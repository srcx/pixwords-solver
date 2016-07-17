package cz.srnet.pixwordssolver;

import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Collectors;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.hamcrest.CoreMatchers;
import org.junit.Test;

@NonNullByDefault
public class WordListTest {

	@SuppressWarnings("null")
	private void doTestList(String pattern, String[] words, String[] matched) {
		WordList wordList = new WordList(new HashSet<String>(Arrays.asList(words)));
		assertThat(wordList.list(pattern).collect(Collectors.toList()), CoreMatchers.hasItems(matched));
	}

	@Test
	public void testList1() {
		doTestList("????", new String[] { "abcd", "abzd" }, new String[] { "abcd", "abzd" });
	}

	@Test
	public void testList2() {
		doTestList("a???", new String[] { "abcd", "abzd" }, new String[] { "abcd", "abzd" });
	}

	@Test
	public void testList3() {
		doTestList("?b??", new String[] { "abcd", "abzd" }, new String[] { "abcd", "abzd" });
	}

	@Test
	public void testList4() {
		doTestList("?bc?", new String[] { "abcd", "abzd" }, new String[] { "abcd" });
	}

	@Test
	public void testList5() {
		doTestList("?b?d", new String[] { "abcd", "abzd" }, new String[] { "abcd", "abzd" });
	}

	@Test
	public void testList6() {
		doTestList("abcd", new String[] { "abcd", "abzd" }, new String[] { "abcd" });
	}

	@Test
	public void testList7() {
		doTestList("aaaa", new String[] { "abcd", "abzd" }, new String[] {});
	}
}
