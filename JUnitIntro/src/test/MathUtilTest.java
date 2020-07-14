package test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

public class MathUtilTest {

	@Test
	public void testAddNum(){
		Assertions.assertEquals(0, MathUtil.addNum(-1,1));
		Assertions.assertEquals(12, MathUtil.addNum(-6,18));
	}
}
