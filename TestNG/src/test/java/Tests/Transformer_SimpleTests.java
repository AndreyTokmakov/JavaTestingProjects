package Tests;


import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ClassesUnderTests.Transformer;

public class Transformer_SimpleTests extends Assert  {
	
	private Transformer transformer = new Transformer();
	
	@BeforeClass
	private void setUp() {
		System.out.println("SetUP");
	}	
	
	@AfterTest
	void tearDown() {
		System.out.println("tearDown");
	}	
	  
	@Test
	public void stringConcatTest() {
	      final String actual = transformer.concatString("Val1", "Val2");
	      final String expected = "Val1Val2";
	      assertEquals(actual, expected);
	}
	
	@Test
	public void sumIntsTest() {
	      final int actual = transformer.sumInts(1, 34);
	      final int expected = 35;
	      assertEquals(actual, expected);
	}	
}
