/****************************************************************************
* Copyright 2021 (C) Andrey Tokmakov
* IgnoredTestsDemo.java class
*
* @name    : IgnoredTestsDemo.java
* @author  : Tokmakov Andrey
* @version : 1.0
* @since   : Jan 9, 2021
****************************************************************************/

package Disabled_Ignored;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

public class IgnoredTestsDemo {
	@Test
	@DisplayName("First demo test")
	public void test1() {
		Assertions.assertEquals("It" + " works!",  "It works!");
	}
    
    @Test
    @DisplayName("Test2. Should be disabled.")
    @Disabled("for demonstration purposes") 
    public void failingTest() {
    	Assertions.fail("a failing test");
    }
    
    @Disabled("Message for ignored test")
    @Test
    public void ignoredTest() {
        System.out.println("will not print it");
    }

    @Test
    @Disabled("Message for ignored test")
    @Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
    public void timeStampTest() {
        while (true);
    }
}
