package Tests;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ClassesUnderTests.Transformer;

class StringConcatTestsData {
    @DataProvider(name = "stringConcatTestsData")
    public static Object[][] dataProviderMethod() {
		return new Object[][]{
			{"Val1", "Val2", "Val1Val2"},
			{"Foo", "Bar", "FooBar"},
		};
    }
}

class IntSumTestsData {
    @DataProvider(name = "intSumTestsData")
    public static Object[][] dataProviderMethod() {
		return new Object[][]{
			{2, 3, 5},
			{10, 20, 30},
			{11, 22, 32},
		};
    }
}

public class Transformer_DataProviderClasses extends Assert  {
	
	private Transformer transformer = new Transformer();
	
	@BeforeClass
	private void setUp() {
		System.out.println("SetUP");
	}	
	
	@AfterTest
	void tearDown() {
		System.out.println("tearDown");
	}	
	
    @Test(dataProvider = "stringConcatTestsData", dataProviderClass = StringConcatTestsData.class)
    public void testStringConcat(String val1, String val2, String expected) {
		final String actual = transformer.concatString(val1, val2);
		assertEquals(actual, expected);
		System.out.println(val1 + "  " + val2 + "  " + expected);
    }
    
    @Test(dataProvider = "intSumTestsData", dataProviderClass = IntSumTestsData.class)
    public void testIntSum(int val1, int val2, int expected) {
		final int actual = transformer.sumInts(val1, val2);
		assertEquals(actual, expected);
		System.out.println(val1 + "  " + val2 + "  " + expected);
    }    
}