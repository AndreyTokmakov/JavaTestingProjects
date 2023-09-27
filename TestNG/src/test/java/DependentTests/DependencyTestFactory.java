package DependentTests;

import org.testng.annotations.Factory;
import org.testng.annotations.Test;
 
public class DependencyTestFactory {
	
	class DependencyTest 
	{
	    private int param;
	 
	    public DependencyTest(int param) {
	        this.param = param;
	    }
	 
	    @Test
	    public void testMethod1() {
	        System.out.println("Test method1 with param values: " + this.param);
	    }
	 
	    @Test(dependsOnMethods = { "testMethod1" })
	    public void testMethod2() {
	        System.out.println("Test method2 with param values: " + this.param);
	    }
	    
	    @Test
	    public void testMethod3() throws Exception {
	        throw new Exception("To make the test fail");
	    }
	 
	    @Test(dependsOnMethods = { "testMethod3" })
	    public void testMethod4() {
	        System.out.println("Test method4 with param values: " + this.param);
	    }
	}
	
    @Factory
    public Object[] factoryMethod() {
        return new Object[] { new DependencyTest(10), 
        					  new DependencyTest(11) };
    }
}
