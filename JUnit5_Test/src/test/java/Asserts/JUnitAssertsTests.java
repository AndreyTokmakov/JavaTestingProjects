package Asserts;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class JUnitAssertsTests {
	@Test
	public void CheckString1() {
		Assertions.assertEquals("It" + " works!",  "It works!");
	}
	
	@Test
	public void CheckString2() {
		Assertions.assertTrue("habr" == "habr");
	}
	
	
    @Test
    public void test1() throws Exception {
    	Assertions.assertEquals(15, 15);
    }

    @Test
    void abortedTest() {
    	Assertions.assertTrue("abc".contains("Z"));
        Assertions.fail("test should have been aborted");
    }
    
    @Test
    @Disabled("for demonstration purposes") 
    void failingTest() {
    	Assertions.fail("a failing test");
    }
}
