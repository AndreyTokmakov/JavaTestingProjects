package JUnit5;


import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class ParamsTest {

    @ParameterizedTest
    @MethodSource("isAdultTestData")
    void isAdultTestNegative(int age, boolean result) {
        assertEquals(result, isAdult(age));
    }

    static Stream<Arguments> isAdultTestData() {
        return Stream.of(arguments(17, false), arguments(0, false), arguments(-1, false));
    }

    public boolean isAdult(int age) {
        return age > 18;
    }
}
