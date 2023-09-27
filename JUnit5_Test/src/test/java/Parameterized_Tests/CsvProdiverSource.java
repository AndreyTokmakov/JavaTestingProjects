package Parameterized_Tests;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CsvProdiverSource {
	@ParameterizedTest
	@CsvSource({
		"java,      4",
		"clojure,   7",
		"python,    6"
	})
	void test_csv(String str, int length) {
		assertEquals(length, str.length());
	}
}
