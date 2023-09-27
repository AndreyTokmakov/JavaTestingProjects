/****************************************************************************
* Copyright 2021 (C) Andrey Tokmakov
* Assert_Same.java class
*
* @name    : Assert_Same.java
* @author  : Tokmakov Andrey
* @version : 1.0
* @since   : Jan 9, 2021
****************************************************************************/

package Asserts;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Assert_Same {
    private final static String originalObject = "howtodoinjava.com";
    private final static String cloneObject = originalObject;
    private final static String otherObject = "example.com";
	
	@Test
	void test1() 
	{    
	    // Test will pass
	    Assertions.assertNotSame(originalObject, otherObject);
	}
	
	@Test
	void test2() 
	{    
	    // Test will fail
	    Assertions.assertNotSame(originalObject, cloneObject);
	}
	
	@Test
	void test3() 
	{    
	    // Test will pass
	    Assertions.assertSame(originalObject, cloneObject);
	}
	
	@Test
	void test4() 
	{    
	    // Test will fail
	    Assertions.assertSame(originalObject, otherObject);
	}
}
