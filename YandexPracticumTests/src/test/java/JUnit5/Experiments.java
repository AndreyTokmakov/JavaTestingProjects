package JUnit5;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;


class Experiments {

    @ParameterizedTest
    @ValueSource(ints = { -1, 0, 17 })
    public void isAdultTest(int age) {
        assertFalse(isAdult(age));
    }

    @ParameterizedTest
    @CsvSource({
            "-1, false",
            "0, false",
            "17, false"
    })
    void isAdultTestNegative(int age, boolean result) {
        assertEquals(result, isAdult(age));
    }

    public boolean isAdult(int age) {
        return age > 18;
    }
}