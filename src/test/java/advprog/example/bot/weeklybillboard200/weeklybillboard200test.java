package advprog.example.bot.weeklybillboard200;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class weeklybillboard200test {
	private weeklybillboard200 billboard200;
	
	@Before
	public void setUp() {
		billboard200 = new weeklybillboard200();
	}
	
	@Test
	public void getTop10Test() {
		String top10 = billboard200.getTop10();
		assertEquals(top10, "Top 10 Weekly Billboard 200");
	}
	
}
