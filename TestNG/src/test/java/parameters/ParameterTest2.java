package parameters;

import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

// To run this u need to run 'As NG test' the XML file named
// testing.xml in the root of this project
 
public class ParameterTest2 extends Assert {
     
    @Parameters({"actualTestValueParameter",
    			 "expectedTestValueParameter",
    			 "TestSuiteGlobalParameter"})
    @Test
    public void Test1(final String actual, 
    				  final String expected, 
    				  final String globalValue) {
    	System.err.println("Test1. TestSuiteGlobalParameter: " + globalValue);
    	System.out.println("Test1. Actual: " + actual + ", Expected: " + expected);
		assertEquals(actual, expected);
    }
 
    @Parameters({"actualTestValueParameter",
    	         "expectedTestValueParameter"})
    @Test
    public void Test2(final String actual, 
    			      final String expected) {
    	System.out.println("Test2. Actual: " + actual + ", Expected: " + expected);
		assertEquals(actual, expected);
    }
    
    @Parameters({"actualTestValueParameter",
				 "expectedTestValueParameter",
				 "TestSuiteGlobalParameter"})
	@Test
	public void Test3(final String actual, 
					  final String expected, 
					  final String globalValue) {
		System.err.println("Test3. TestSuiteGlobalParameter: " + globalValue);
		System.out.println("Test3. Actual: " + actual + ", Expected: " + expected);
		assertEquals(actual, expected);
	}
}