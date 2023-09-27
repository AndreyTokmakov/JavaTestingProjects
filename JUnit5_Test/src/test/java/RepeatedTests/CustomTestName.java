package RepeatedTests;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CustomTestName {
	
    @RepeatedTest(3)
    void math_add_1() {
        System.out.println("Run math_add_1()");
        assertEquals(2, 1 + 1);
    }

    @RepeatedTest(value = 3, name = RepeatedTest.LONG_DISPLAY_NAME)
    void math_add_2() {
        System.out.println("Run math_add_2()");
        assertEquals(2, 1 + 1);
    }

    @RepeatedTest(value = 3, name = "{displayName} - ABC - {currentRepetition}/{totalRepetitions}")
    void math_add_3(RepetitionInfo info) {
        System.out.print("Run math_add_3(): [" + info.getCurrentRepetition());
        System.out.println(" of " + info.getTotalRepetitions() +  "]");
        assertEquals(2, 1 + 1);
    }
}
