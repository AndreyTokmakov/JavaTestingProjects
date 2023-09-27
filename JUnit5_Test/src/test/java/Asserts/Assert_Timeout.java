package Asserts;

import static java.time.Duration.ofMillis;
import static java.time.Duration.ofMinutes;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeout;

import java.time.Duration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Assert_Timeout {
    @Test
    void timeoutNotExceeded_OK() {
        // The following assertion succeeds.
        assertTimeout(ofMinutes(2), () -> {
            // Do some work
        });
    }
    
    @Test
    void timeoutNotExceeded_Failed() {
        // The following assertion succeeds.
        assertTimeout(ofMillis(100), () -> {
        	Thread.currentThread();
			Thread.sleep(500);
        });
    }
    
    @Test
    void timeoutNotExceededWithResult_OK() {
        // The following assertion succeeds, and returns the supplied object.
        String actualResult = assertTimeout(ofMinutes(2), () -> {
            return "a result";
        });
        assertEquals("a result", actualResult);
    }
    
    @Test
    void testCase() {
        //This will pass
        Assertions.assertTimeout(Duration.ofMinutes(1), () -> {
            return "result";
        });
         
        //This will fail
        Assertions.assertTimeout(Duration.ofMillis(100), () -> {
            Thread.sleep(200);
            return "result";
        });
         
        //This will fail
        Assertions.assertTimeoutPreemptively(Duration.ofMillis(100), () -> {
            Thread.sleep(200);
            return "result";
        });
    }
    
    @Test
    void timeoutNotExceededWithMethod_Failed() {
        // The following assertion invokes a method reference and returns an object.
        String actualGreeting = assertTimeout(ofMillis(200), Assert_Timeout::greeting);
        assertEquals("Hello, World!", actualGreeting);
    }
    
    private static String greeting() {
    	Thread.currentThread();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        return "Hello, World!";
    }
}
