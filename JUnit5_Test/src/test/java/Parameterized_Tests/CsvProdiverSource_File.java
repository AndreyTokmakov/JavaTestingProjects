package Parameterized_Tests;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CsvProdiverSource_File {
	
	// Skip the first line
    @ParameterizedTest
    @CsvFileSource(resources = "/test_data/simple.csv", numLinesToSkip = 1)
    void test_csv_file(String str, int length) {
        assertEquals(length, str.length());
    }
}
