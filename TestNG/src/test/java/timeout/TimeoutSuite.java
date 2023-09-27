package timeout;

import org.testng.annotations.Test;

public class TimeoutSuite 
{
    @Test
    public void timeTestOne() throws InterruptedException {
        Thread.sleep(1000);
        System.out.println("Time test method one");
    }
 
    @Test
    public void timeTestTwo() throws InterruptedException {
        Thread.sleep(400);
        System.out.println("Time test method two");
    }
}