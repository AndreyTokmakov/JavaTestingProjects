package Parameterized_Tests;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class MultipleArgumentsTests {
    @ParameterizedTest
    @MethodSource("stringIntAndListProvider")
    void testWithMultiArgMethodSource(String str_param, int value, List<String> list) {
        assertTrue(str_param.length() > 0);
        assertEquals(value, list.size());
    }

    static Stream<Arguments> stringIntAndListProvider() {
        return Stream.of(arguments("Param1" /* str_param */, 3 /* value */, Arrays.asList("a", "b", "c")),
                	     arguments("Param2" /* str_param */, 3 /* value */, Arrays.asList("x", "y", "z")),
                	     arguments("Param3" /* str_param */, 2 /* value */, Arrays.asList("null", "null"))
        );
    }
}
