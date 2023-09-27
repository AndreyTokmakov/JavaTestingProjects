package factory;

import org.testng.annotations.Factory;
import org.testng.annotations.Test;

class SimpleTest {
    @Test
    public void simpleTest() {
        System.out.println("Simple Test Method.");
    }
}
 
public class FactoryTests_1 {
    @Factory
    public Object[] factoryMethod() {
        return new Object[] { new SimpleTest(), new SimpleTest() };
    }
}
