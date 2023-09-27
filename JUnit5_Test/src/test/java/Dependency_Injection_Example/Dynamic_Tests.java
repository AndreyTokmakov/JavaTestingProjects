/****************************************************************************
* Copyright 2020 (C) Andrey Tokmakov
* Dynamic_Tests class
*
* @name    : Dynamic_Tests.java
* @author  : Tokmakov Andrey
* @version : 1.0
* @since   : November 23, 2020
****************************************************************************/ 

package Dependency_Injection_Example;

import java.util.Arrays;
import java.util.Collection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import Utilities.MathUtil;

public class Dynamic_Tests {
	
	@Test
	void test_Add() {
		Assertions.assertEquals(5, MathUtil.add(3, 2));
	}
	
	
	@TestFactory
    Collection<DynamicTest> dynamicTestsFromCollection() {
        return Arrays.asList(
        		DynamicTest.dynamicTest("1st dynamic test", () -> Assertions.assertTrue(MathUtil.isPrime(13))),
        		DynamicTest.dynamicTest("2nd dynamic test", () -> Assertions.assertEquals(5, MathUtil.devide(25, 5)))
        );
    }

	@Test
	void test_Devide() {
		Assertions.assertEquals(5, MathUtil.devide(25, 5));
	}
	
	@Test
	void testIs_Prime() {
		Assertions.assertTrue(MathUtil.isPrime(13));
	}
}
