package testing;
import development.JUnitpractice;

import static org.junit.Assert.*;

import org.junit.Test;

public class testSquare {

	@Test
	public void test() {
		JUnitpractice jp = new JUnitpractice();
		int output = jp.square(5);
		assertEquals(25, output);
	}

}
