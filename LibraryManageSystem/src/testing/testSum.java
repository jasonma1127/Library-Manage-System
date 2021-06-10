package testing;
import development.JUnitpractice;

import static org.junit.Assert.*;

import org.junit.Test;

public class testSum {

	@Test
	public void test() {
		JUnitpractice jp = new JUnitpractice();
		int output = jp.sum(3, 5);
		assertEquals(8, output);
	}

}