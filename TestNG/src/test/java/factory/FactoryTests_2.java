package factory;

import org.testng.annotations.Factory;
import org.testng.annotations.Test;

class SimpleTest_2  {
    private int param;
 
    public SimpleTest_2(int param) {
        this.param = param;
    }
 
    @Test
    public void testMethodOne() {
        int opValue = param + 1;
        System.out.println("Test method one output: " + opValue);
    }
 
    @Test
    public void testMethodTwo() {
        int opValue = param + 2;
        System.out.println("Test method two output: " + opValue);
    }
}
 
public class FactoryTests_2 {
    @Factory
    public Object[] factoryMethod() {
        return new Object[] { new SimpleTest_2(10), 
        					  new SimpleTest_2(11) };
    }
}
