package DependentTests;


import org.testng.Assert;
import org.testng.annotations.Test;

public class DependencyTest {
    @Test
    public void initEnvironmentTest() {
        System.out.println("This is initEnvironmentTest");
    }
    
    @Test
    public void beforeTest1() {
        System.out.println("Before test action 1");
        Assert.assertEquals(1, 1);
    }

    @Test(dependsOnMethods = {"initEnvironmentTest", "beforeTest1"})
    public void testmethod() {
        System.out.println("This is testmethod");
    }
}