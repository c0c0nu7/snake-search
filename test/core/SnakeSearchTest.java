package core;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class SnakeSearchTest {

	@Test
	public void testSearch() {
		assertThat(SnakeSearch.search("res/grid.txt", "INSURANCE"), is(1416));
		assertThat(SnakeSearch.search("res/grid2.txt", "EINS"), is(4));
		assertThat(SnakeSearch.search("res/grid3.txt", "ENE"), is(6));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testEmptySearchString() {
		SnakeSearch.search("res/grid.txt", "");
	}
	
}
