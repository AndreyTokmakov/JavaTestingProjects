package TestInfo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import Utilities.MathUtil;

public class TestInfoTest {
	
	@BeforeEach
	void beforeEach(TestInfo testInfo) {
		// here initialize setup for each test
		System.out.println("Before each test: " + testInfo.getTestMethod().get().getName());
		System.out.println("                  " + testInfo.getTestMethod().get().toGenericString() + "\n");
	}
	
	@Tag("my-tag_1")
	@DisplayName("MathUtil.add_test()")
	@Test
	void test_Add(TestInfo testInfo) {
		System.out.println("-------------- Running test Start test_Add() -----------------");
		
		System.out.println("Test info:");
		System.out.println("   Name  : " + testInfo.getDisplayName());
		System.out.println("   Tags  : " + testInfo.getTags());
		System.out.println("   Method info:");
		System.out.println("      name        : " + testInfo.getTestMethod().get().getName());
		System.out.println("      name long   : " + testInfo.getTestMethod().get().toGenericString());
		System.out.println("      params count: " + testInfo.getTestMethod().get().getParameterCount());
		
	
		Assertions.assertEquals(5, MathUtil.add(3, 2));
		
		System.out.println("-------------- Done -----------------");
	}	
}
