package Parameterized_Tests;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.api.Assertions;

class Numbers {
    public static boolean isOdd(int number) {
        return number % 2 != 0;
    }
}


public class ParameterizedTests {

	@ParameterizedTest
	@ValueSource(strings = { "racecar", "radar", "able was I ere I saw elba" })
	void palindromes(String candidate) {
		Assertions.assertTrue(true);
	}
	
	@ParameterizedTest
	@ValueSource(ints = {1, 3, 5, -3, 15, Integer.MAX_VALUE}) // six numbers
	void isOdd_ShouldReturnTrueForOddNumbers(int number) {
		Assertions.assertTrue(Numbers.isOdd(number));
	}
}
