/****************************************************************************
* Copyright 2020 (C) Andrey Tokmakov
* DynamicTests_Timeout_Test test
*
* @name    : DynamicTests_Timeout_Test.java
* @author  : Tokmakov Andrey
* @version : 1.0
* @since   : November 26, 2020
****************************************************************************/ 

package DynamicTests;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import Utilities.MathUtil;

public class DynamicTests_Timeout_Test {
	@TestFactory
	List<DynamicTest> test_dynamicTests_AssertTimeouts() {
	    return Arrays.asList(
	        
	    	DynamicTest.dynamicTest("1st dynamic test", () -> {
	    		Assertions.assertTimeout(Duration.ofSeconds(5), () -> { 
	                         TimeUnit.SECONDS.sleep(1);
	                         Assertions.assertEquals(5, MathUtil.add(3, 2));
	                         System.out.println("Dynamic Test 3");
	                      });
	        }),
	        
	    	DynamicTest.dynamicTest("2nd dynamic test", () -> {
	    		Assertions.assertTimeoutPreemptively(Duration.ofSeconds(5), () -> { 
	                         TimeUnit.SECONDS.sleep(2);
	                         Assertions.assertEquals(5, MathUtil.add(3, 2));
	                         System.out.println("Dynamic Test 4");
	                      });
	        }));
	  }
}
