package Tests;

import org.testng.annotations.*;

public class TestNGIgnoreTest {
    @Test(enabled = false)
    public void testsetProperty() {
        System.err.println("This test shall not be runned!!!");
    }
}
