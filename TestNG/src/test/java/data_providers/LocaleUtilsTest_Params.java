package data_providers;

import java.util.Locale;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ClassesUnderTests.LocaleUtils;

public class LocaleUtilsTest_Params extends Assert {

	@DataProvider
	public Object[][] dataProvider1() {
		return new Object[][]{
			{null, null},
			{"", LocaleUtils.ROOT_LOCALE},
			{"en", Locale.ENGLISH},
			{"en_US", Locale.US},
			{"en_GB", Locale.UK},
			{"ru", new Locale("ru")},
			{"ru_RU_some_variant", new Locale("ru", "RU", "some_variant")},
		};
	}
	
	@DataProvider(name = "data-provider")
	public Object[][] dataProvider2() {
		return new Object[][] { { "data one" }, { "data two" } };
	}

	@Test(dataProvider = "dataProvider1")
	public void testParseLocale(String locale, Locale expected) {
		final Locale actual = LocaleUtils.parseLocale(locale);
		assertEquals(actual, expected);
	}
	 
	@Test(dataProvider = "data-provider")
	public void testMethod(String data) {
		System.out.println("Data is: " + data);
	}
}