package data_providers;


import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ClassesUnderTests.Transformer;

public class Transformer_Params extends Assert  {
	
	private Transformer transformer = null;
	
	@BeforeClass
	private void setUp() {
		transformer = new Transformer();
	}	
	
	@AfterTest
	void tearDown() {
		transformer.close();
	}	
	
	@DataProvider
	public Object[][] concatStringsData() {
		return new Object[][]{
			{"Value1", "Value1", "Value1Value1"},
			{"Value2", "Value2", "Value2Value2"},
			{"Value3", "Value3", "Value3Value3"},
			{"Value4", "Value4", "Value2Value4"},
			{"Value5", "Value5", "Value5Value5"},
			{"Value6", "Value6", "Value6Value6"},
			{"Value7", "Value7", "Value7Value7"},
			{"Value8", "Value8", "Value1Value8"},
		};
	}
	  
	@Test(dataProvider = "concatStringsData")
	public void testParseLocale(String val1, String val2, String expected) {
		final String actual = transformer.concatString(val1, val2);
		assertEquals(actual, expected);
		System.out.println(val1 + "  " + val2 + "  " + expected);
	}
}
