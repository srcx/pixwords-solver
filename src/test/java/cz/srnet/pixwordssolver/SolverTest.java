package cz.srnet.pixwordssolver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.Collection;

import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;

public class SolverTest {

	@Before
	public void prepare() {
		solver = new Solver();
	}
	
	Solver solver;
	
	@Test
	public void test1() {
		Collection<String> result = solver.solve("", "");
		assertEquals(1, result.size());
		assertThat(result, CoreMatchers.hasItem(""));
	}

	@Test
	public void test2() {
		Collection<String> result = solver.solve("a", "");
		assertEquals(1, result.size());
		assertThat(result, CoreMatchers.hasItem("a"));
	}

	@Test
	public void test3() {
		Collection<String> result = solver.solve("?", "a");
		assertEquals(1, result.size());
		assertThat(result, CoreMatchers.hasItem("a"));
	}

	@Test
	public void test4() {
		Collection<String> result = solver.solve("a?", "ab");
		assertEquals(2, result.size());
		assertThat(result, CoreMatchers.hasItems("aa", "ab"));
	}

	@Test
	public void test5() {
		Collection<String> result = solver.solve("a", "ab");
		assertEquals(1, result.size());
		assertThat(result, CoreMatchers.hasItem("a"));
	}

	@Test
	public void test6() {
		Collection<String> result = solver.solve("a?b?", "cde");
		assertEquals(6, result.size());
		assertThat(result, CoreMatchers.hasItems("acbd", "acbe", "adbc", "adbe", "aebc", "aebd" ));
	}

	@Test
	public void test7() {
		Collection<String> result = solver.solve("a?", "bb");
		assertEquals(1, result.size());
		assertThat(result, CoreMatchers.hasItem("ab"));
	}

	@Test
	public void test8() {
		Collection<String> result = solver.solve("a?b?", "cc");
		assertEquals(1, result.size());
		assertThat(result, CoreMatchers.hasItem("acbc"));
	}

	@Test
	public void test9() {
		Collection<String> result = solver.solve("a?b?", "ccd");
		assertEquals(3, result.size());
		assertThat(result, CoreMatchers.hasItems("acbc", "acbd", "adbc"));
	}
}
