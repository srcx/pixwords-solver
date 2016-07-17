package cz.srnet.pixwordssolver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.hamcrest.CoreMatchers;
import org.junit.Test;

public class SolverTest {

	@Test
	public void testSolve1() {
		Solver solver = new Solver("", "");
		assertEquals(1, solver.getEstimatedPossibilities());
		Collection<String> result = solver.solve();
		assertEquals(1, result.size());
		assertThat(result, CoreMatchers.hasItem(""));
	}

	@Test
	public void testMatches1() {
		Solver solver = new Solver("", "");
		assertTrue(solver.matches(""));
	}

	@Test
	public void testSolve2() {
		Solver solver = new Solver("a", "");
		assertEquals(1, solver.getEstimatedPossibilities());
		Collection<String> result = solver.solve();
		assertEquals(1, result.size());
		assertThat(result, CoreMatchers.hasItem("a"));
	}

	@Test
	public void testMatches2() {
		Solver solver = new Solver("a", "");
		assertTrue(solver.matches("a"));
		assertFalse(solver.matches("A"));
		assertFalse(solver.matches("z"));
	}

	@Test
	public void testSolve3() {
		Solver solver = new Solver("?", "a");
		assertEquals(1, solver.getEstimatedPossibilities());
		Collection<String> result = solver.solve();
		assertEquals(1, result.size());
		assertThat(result, CoreMatchers.hasItem("a"));
	}

	@Test
	public void testMatches3() {
		Solver solver = new Solver("?", "a");
		assertTrue(solver.matches("a"));
		assertFalse(solver.matches("A"));
		assertFalse(solver.matches("z"));
	}

	@Test
	public void testSolve4() {
		Solver solver = new Solver("a?", "ab");
		assertEquals(2, solver.getEstimatedPossibilities());
		Collection<String> result = solver.solve();
		assertEquals(2, result.size());
		assertThat(result, CoreMatchers.hasItems("aa", "ab"));
	}

	@Test
	public void testMatches4() {
		Solver solver = new Solver("a?", "ab");
		assertTrue(solver.matches("aa"));
		assertTrue(solver.matches("ab"));
		assertFalse(solver.matches("az"));
	}

	@Test
	public void testSolve5() {
		Solver solver = new Solver("a", "ab");
		assertEquals(1, solver.getEstimatedPossibilities());
		Collection<String> result = solver.solve();
		assertEquals(1, result.size());
		assertThat(result, CoreMatchers.hasItem("a"));
	}

	@Test
	public void testSolve6() {
		Solver solver = new Solver("a?b?", "cde");
		assertEquals(6, solver.getEstimatedPossibilities());
		Collection<String> result = solver.solve();
		assertEquals(6, result.size());
		assertThat(result, CoreMatchers.hasItems("acbd", "acbe", "adbc",
				"adbe", "aebc", "aebd"));
	}

	@Test
	public void testMatches6() {
		Solver solver = new Solver("a?b?", "cde");
		assertTrue(solver.matches("acbd"));
		assertTrue(solver.matches("acbe"));
		assertTrue(solver.matches("adbc"));
		assertTrue(solver.matches("adbe"));
		assertTrue(solver.matches("aebc"));
		assertTrue(solver.matches("aebd"));
		assertFalse(solver.matches("acbc"));
		assertFalse(solver.matches("zzzz"));
	}

	@Test
	public void testSolve7() {
		Solver solver = new Solver("a?", "bb");
		assertEquals(2, solver.getEstimatedPossibilities());
		Collection<String> result = solver.solve();
		assertEquals(1, result.size());
		assertThat(result, CoreMatchers.hasItem("ab"));
	}

	@Test
	public void testSolve8() {
		Solver solver = new Solver("a?b?", "cc");
		assertEquals(2, solver.getEstimatedPossibilities());
		Collection<String> result = solver.solve();
		assertEquals(1, result.size());
		assertThat(result, CoreMatchers.hasItem("acbc"));
	}

	@Test
	public void testSolve9() {
		Solver solver = new Solver("a?b?", "ccd");
		assertEquals(6, solver.getEstimatedPossibilities());
		Collection<String> result = solver.solve();
		assertEquals(3, result.size());
		assertThat(result, CoreMatchers.hasItems("acbc", "acbd", "adbc"));
	}
}
