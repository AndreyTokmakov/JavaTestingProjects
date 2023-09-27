package Dependency_Injection_Example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import Utilities.MathUtil;

public class TestInfoTest {
	
	// for each test a new instance create (lifecycle per-method default behaviour)
	public TestInfoTest(TestInfo info) {
		System.out.println("------------------");
		System.out.println("$$$ " + info.getDisplayName()+" $$$");
		System.out.println("------------------");
	}
	
	@BeforeEach
	void beforeEach(TestInfo testInfo) {
		// here initialize setup for each test
		System.out.println("Before Strat test >>>> "+testInfo.getTestMethod().get().getName());
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
