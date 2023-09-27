/****************************************************************************
* Copyright 2021 (C) Andrey Tokmakov
* Assert_EQUALS.java class
*
* @name    : Assert_EQUALS.java
* @author  : Tokmakov Andrey
* @version : 1.0
* @since   : Jan 9, 2021
****************************************************************************/

package Asserts;

import java.util.function.Supplier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Assert_Equals {
	private final Utilities.Calculator calculator = new Utilities.Calculator();
	
	@Test
	void testCase() 
	{
	    // Test will pass
	    Assertions.assertNotEquals(3, calculator.add(2, 2));
	     
	    // Test will fail 
	    Assertions.assertNotEquals(4, calculator.add(2, 2), "Calculator.add(2, 2) test failed");
	     
	    // Test will fail 
	    Supplier<String> messageSupplier  = ()-> "Calculator.add(2, 2) test failed";
	    Assertions.assertNotEquals(4, calculator.add(2, 2), messageSupplier);
	}
	
	
	@Test
	void asserts1() {
		Assertions.assertEquals(2, 2);
		Assertions.assertEquals(4, 5, "The optional failure message is now the last parameter");
		Assertions.assertTrue('a' < 'b', () -> "Assertion messages can be lazily evaluated -- "  + "to avoid constructing complex messages unnecessarily.");
	}
	
	@Test
	void asserts2() {
		Assertions.assertEquals(2, 2);
		Assertions.assertEquals(4, 4, "The optional failure message is now the last parameter");
		Assertions.assertTrue('a' > 'b', () -> "Assertion messages can be lazily evaluated -- "  + "to avoid constructing complex messages unnecessarily.");
	}
}
