package parameters;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import ClassesUnderTests.Transformer;

public class Params_SetUP_BeforeClass extends Assert {
	
	private Transformer transformer = new Transformer();
	private String value1;
	private String value2;
	private String expected;
	
	@Parameters({"value1", "value2", "expected"})
	@BeforeClass
	public void setUpData(final String val1, 
						  final String val2, 
						  // @Optional("sa") final String username, 
						  @Optional("OPTIONAL_VALUE") final String exp) {
		this.value1 = val1;
		this.value2 = val2;
		this.expected = exp;
		
		System.out.println("Setting up the test data:");
		System.out.println("	value1: " + value1);
		System.out.println("	value2: " + value2);
		System.out.println("	expected: " + expected);
	}
	  
	@Test
	public void testParseLocale() {
		final String actual = transformer.concatString(this.value1, this.value2);
		assertEquals(actual, this.expected);
		System.out.println(value1 + "  " + value2 + "  " + expected);
	}
}