package Parameterized_Tests;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MethodSourceTests {
	
    // this need static
    static Stream<String> stringProvider() {
        return Stream.of("Valu1", "Valu12", "Valu3", "Valu4", "Valu5");
    }	
    
    // this need static
    static IntStream rangeProvider() {
        return IntStream.range(0, 10);
    }

	
    @ParameterizedTest(name = "#{index} - Test with String : {0}")
    @MethodSource("stringProvider")
    void test_method_string(String arg) {
        assertNotNull(arg);
    }

    @ParameterizedTest(name = "#{index} - Test with Int : {0}")
    @MethodSource("rangeProvider")
    void test_method_int(int arg) {
        assertTrue(arg < 10);
    }
    
    @ParameterizedTest
    @MethodSource
    void testWith_Default_local_MethodSource(String arg) {
    	System.out.println("testWith_Default_local_MethodSource(arg) => "+arg);
        assertNotNull(arg);
    }  
    
    static Stream<String> testWith_Default_local_MethodSource() {
    	System.out.println("Default factory called");
        return Stream.of("Peter", "Philip", "John");
    }
}
