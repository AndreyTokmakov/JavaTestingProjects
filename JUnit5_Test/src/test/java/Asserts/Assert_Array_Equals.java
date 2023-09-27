/****************************************************************************
* Copyright 2021 (C) Andrey Tokmakov
* Assert_ArrayEquals.java class
*
* @name    : Assert_ArrayEquals.java
* @author  : Tokmakov Andrey
* @version : 1.0
* @since   : Jan 9, 2021
****************************************************************************/

package Asserts;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

public class Assert_Array_Equals {
	
	@Test
	@Order(2)
	void test2() 
	{
	    // Test will pass
	    Assertions.assertArrayEquals(new int[]{1,2,3}, new int[]{1,2,3}, "Array Equal Test");
	     
	    // Test will fail because element order is different
	    Assertions.assertArrayEquals(new int[]{1,2,3}, new int[]{1,3,2}, "Array Equal Test");
	     
	    // Test will fail because number of elements are different
	    Assertions.assertArrayEquals(new int[]{1,2,3}, new int[]{1,2,3,4}, "Array Equal Test");
	}
	
	@Test
	@Order(1)
	void test1() 
	{
		Iterable<Integer> listOne = new ArrayList<Integer>(Arrays.asList(1,2,3,4));
	    Iterable<Integer> listTwo = new ArrayList<Integer>(Arrays.asList(1,2,3,4));
	    Iterable<Integer> listThree = new ArrayList<Integer>(Arrays.asList(1,2,3));
	    Iterable<Integer> listFour = new ArrayList<Integer>(Arrays.asList(1,2,3,4));
	    
	    //Test will pass
	    Assertions.assertIterableEquals(listOne, listTwo);
	     
	    //Test will fail
	    Assertions.assertIterableEquals(listOne, listFour);
	     
	    //Test will fail
	    Assertions.assertIterableEquals(listTwo, listFour);
	}
}
