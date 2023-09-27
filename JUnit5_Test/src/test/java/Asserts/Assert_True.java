/****************************************************************************
* Copyright 2021 (C) Andrey Tokmakov
* Assert_True.java class
*
* @name    : Assert_True.java
* @author  : Tokmakov Andrey
* @version : 1.0
* @since   : Jan 9, 2021
****************************************************************************/

package Asserts;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Assert_True {
	@Test
	void test1() {
		Assertions.assertTrue('a' < 'b', () -> "Assertion messages can be lazily evaluated -- "  + "to avoid constructing complex messages unnecessarily.");
	}
	
	@Test
	void test2() {
		Assertions.assertTrue('a' > 'b', () -> "Assertion messages can be lazily evaluated -- "  + "to avoid constructing complex messages unnecessarily.");
	}
}
