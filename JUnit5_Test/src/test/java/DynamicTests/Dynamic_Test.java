/****************************************************************************
* Copyright 2020 (C) Andrey Tokmakov
* Dynamic_Test test
*
* @name    : Dynamic_Test.java
* @author  : Tokmakov Andrey
* @version : 1.0
* @since   : November 26, 2020
****************************************************************************/ 

package DynamicTests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DynamicTest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import Utilities.MathUtil;

public class Dynamic_Test {

	@Test // Static test 1
	void test_Add() {
		Assertions.assertEquals(5, MathUtil.add(3, 2));
	}

	// This method produces Dynamic test cases
	@TestFactory
	Collection<DynamicTest> dynamicTestsFromCollection() {
		return Arrays.asList(DynamicTest.dynamicTest("1st dynamic test", () -> Assertions.assertTrue(MathUtil.isPrime(13))),
				DynamicTest.dynamicTest("2nd dynamic test", () -> Assertions.assertEquals(5, MathUtil.devide(25, 5))));
	}
	
	@TestFactory
	Iterable<DynamicTest> dynamicTestsWithIterable() {
	    return Arrays.asList(DynamicTest.dynamicTest("Add test",  () -> Assertions.assertEquals(2, Math.addExact(1, 1))),
	    					 DynamicTest.dynamicTest("Multiply Test", () -> Assertions.assertEquals(4, Math.multiplyExact(2, 2))));
	}
	
	@TestFactory
	Iterator<DynamicTest> dynamicTestsWithIterator() {
	    return Arrays.asList(DynamicTest.dynamicTest("Add test", () -> Assertions.assertEquals(2, Math.addExact(1, 1))),
	                         DynamicTest.dynamicTest("Multiply Test", () -> Assertions.assertEquals(4, Math.multiplyExact(2, 2)))).iterator();
	}
	
	@TestFactory
	Stream<DynamicTest> dynamicTestsFromIntStream() {
	    return IntStream.iterate(0, n -> n + 2).limit(10).mapToObj(n -> DynamicTest.dynamicTest("test" + n, () -> Assertions.assertTrue(n % 2 == 0)));
	}

	@Test // Static test 2
	void test_Devide() {
		Assertions.assertEquals(5, MathUtil.devide(25, 5));
	}
}
