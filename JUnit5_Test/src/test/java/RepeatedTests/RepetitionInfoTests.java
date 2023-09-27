package RepeatedTests;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RepetitionInfoTests {
	@RepeatedTest(3)
	void math_add_4(RepetitionInfo repetitionInfo) {
		System.out.println("Repetition #" + repetitionInfo.getCurrentRepetition());
		System.out.println("TotalRepetitions " + repetitionInfo.getTotalRepetitions());
		assertEquals(3, repetitionInfo.getTotalRepetitions());
	}
}
