package core;

import static org.junit.Assert.*;
import static org.hamcrest.core.Is.is;
import org.junit.Test;

public class SnakeSearchTest {

	@Test
	public void testSearch() {
		assertThat(SnakeSearch.search("res/grid.txt", "INSURANCE"), is(1416));
	}
	
}
