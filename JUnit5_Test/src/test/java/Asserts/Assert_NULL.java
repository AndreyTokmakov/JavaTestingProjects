/****************************************************************************
* Copyright 2021 (C) Andrey Tokmakov
* Assert_NULL.java class
*
* @name    : Assert_NULL.java
* @author  : Tokmakov Andrey
* @version : 1.0
* @since   : Jan 9, 2021
****************************************************************************/

package Asserts;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Assert_NULL {
	@Test
	void Asserts_for_NULL() 
	{    
	    String nullString = null;
	    String notNullString = "howtodoinjava.com";
	     
	    // Test will pass
	    Assertions.assertNotNull(notNullString);
	     
	    // Test will fail
	    Assertions.assertNotNull(nullString);
	     
	    // Test will pass
	    Assertions.assertNull(nullString);
	 
	    // Test will fail
	    Assertions.assertNull(notNullString);
	}
}
