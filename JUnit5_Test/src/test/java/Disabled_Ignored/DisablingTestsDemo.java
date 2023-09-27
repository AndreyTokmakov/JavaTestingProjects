/****************************************************************************
* Copyright 2020 (C) Andrey Tokmakov
* DisablingTestsDemo.java class
*
* @name    : DisablingTestsDemo.java
* @author  : Tokmakov Andrey
* @version : 1.0
* @since   : Dec 20, 2020
****************************************************************************/

package Disabled_Ignored;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


public class DisablingTestsDemo {
	@Test
	@DisplayName("First demo test")
	public void test1() {
		Assertions.assertEquals("It" + " works!",  "It works!");
	}
    
    @Test
    @DisplayName("Test2. Should be disabled.")
    @Disabled("for demonstration purposes") 
    void failingTest() {
    	Assertions.fail("a failing test");
    }
}
