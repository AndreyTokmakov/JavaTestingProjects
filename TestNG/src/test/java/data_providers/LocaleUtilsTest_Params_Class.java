package data_providers;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ClassesUnderTests.LocaleUtils;

class LocaleUtilsTestData {
	@DataProvider(name = "getCandidateLocalesData")
	public static Object[][] getCandidateLocalesData() {
		return new Object[][]{
			{null, Arrays.asList(LocaleUtils.ROOT_LOCALE)},
			{LocaleUtils.ROOT_LOCALE, Arrays.asList(LocaleUtils.ROOT_LOCALE)},
			{Locale.ENGLISH, Arrays.asList(Locale.ENGLISH, LocaleUtils.ROOT_LOCALE)},
			{Locale.US, Arrays.asList(Locale.US, Locale.ENGLISH, LocaleUtils.ROOT_LOCALE)},
			{new Locale("en", "US", "xxx"), Arrays.asList(new Locale("en", "US", "xxx"), Locale.US, Locale.ENGLISH, LocaleUtils.ROOT_LOCALE)},
		};
	}
}

public class LocaleUtilsTest_Params_Class extends Assert {
	// other tests
	@Test(dataProvider = "getCandidateLocalesData", dataProviderClass = LocaleUtilsTestData.class)
	public void testGetCandidateLocales(Locale locale, List<Locale> expected) {
		final List<Locale> actual = LocaleUtils.getCandidateLocales(locale);
		assertEquals(actual, expected);
	}
}