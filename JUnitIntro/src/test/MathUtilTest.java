package test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
/**
 * Class intended to deploy test cases for {@link MathUtil} using JUnit5.
 * @author Ashish Tulsankar
 *
 */
public class MathUtilTest {

	@Test
	public void testAddNum(){
		Assertions.assertEquals(0, MathUtil.addNum(-1,1));
		Assertions.assertEquals(12, MathUtil.addNum(-6,18));
	}
	
	@Test
	public void testDivNum(){
		Assertions.assertEquals(0, MathUtil.divNum(-1,0));
		Assertions.assertEquals(0, MathUtil.divNum(1,0));
		Assertions.assertEquals(0, MathUtil.divNum(0,0));
		Assertions.assertEquals(3, MathUtil.divNum(18,6));
		Assertions.assertEquals(-3, MathUtil.divNum(-18,6));
	}
}
