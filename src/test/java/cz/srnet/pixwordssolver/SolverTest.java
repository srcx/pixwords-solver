package cz.srnet.pixwordssolver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.Collection;

import org.hamcrest.CoreMatchers;
import org.junit.Test;

public class SolverTest {

	@Test
	public void test1() {
		Solver solver = new Solver("", "");
		assertEquals(1, solver.getEstimatedPossibilities());
		Collection<String> result = solver.solve();
		assertEquals(1, result.size());
		assertThat(result, CoreMatchers.hasItem(""));
	}

	@Test
	public void test2() {
		Solver solver = new Solver("a", "");
		assertEquals(1, solver.getEstimatedPossibilities());
		Collection<String> result = solver.solve();
		assertEquals(1, result.size());
		assertThat(result, CoreMatchers.hasItem("a"));
	}

	@Test
	public void test3() {
		Solver solver = new Solver("?", "a");
		assertEquals(1, solver.getEstimatedPossibilities());
		Collection<String> result = solver.solve();
		assertEquals(1, result.size());
		assertThat(result, CoreMatchers.hasItem("a"));
	}

	@Test
	public void test4() {
		Solver solver = new Solver("a?", "ab");
		assertEquals(2, solver.getEstimatedPossibilities());
		Collection<String> result = solver.solve();
		assertEquals(2, result.size());
		assertThat(result, CoreMatchers.hasItems("aa", "ab"));
	}

	@Test
	public void test5() {
		Solver solver = new Solver("a", "ab");
		assertEquals(1, solver.getEstimatedPossibilities());
		Collection<String> result = solver.solve();
		assertEquals(1, result.size());
		assertThat(result, CoreMatchers.hasItem("a"));
	}

	@Test
	public void test6() {
		Solver solver = new Solver("a?b?", "cde");
		assertEquals(6, solver.getEstimatedPossibilities());
		Collection<String> result = solver.solve();
		assertEquals(6, result.size());
		assertThat(result, CoreMatchers.hasItems("acbd", "acbe", "adbc",
				"adbe", "aebc", "aebd"));
	}

	@Test
	public void test7() {
		Solver solver = new Solver("a?", "bb");
		assertEquals(2, solver.getEstimatedPossibilities());
		Collection<String> result = solver.solve();
		assertEquals(1, result.size());
		assertThat(result, CoreMatchers.hasItem("ab"));
	}

	@Test
	public void test8() {
		Solver solver = new Solver("a?b?", "cc");
		assertEquals(2, solver.getEstimatedPossibilities());
		Collection<String> result = solver.solve();
		assertEquals(1, result.size());
		assertThat(result, CoreMatchers.hasItem("acbc"));
	}

	@Test
	public void test9() {
		Solver solver = new Solver("a?b?", "ccd");
		assertEquals(6, solver.getEstimatedPossibilities());
		Collection<String> result = solver.solve();
		assertEquals(3, result.size());
		assertThat(result, CoreMatchers.hasItems("acbc", "acbd", "adbc"));
	}
}
