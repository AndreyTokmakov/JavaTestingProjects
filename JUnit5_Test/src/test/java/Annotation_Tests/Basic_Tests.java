package Annotation_Tests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

class Basic_Tests {

	@Test
	public void CheckString2() {
		Assertions.assertTrue("habr" == "habr");
	}
	
    @Test
    public void test1() throws Exception {
    	Assertions.assertEquals(15, 15);
    }
}
