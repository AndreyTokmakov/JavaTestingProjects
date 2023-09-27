package Asserts;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import Utilities.Calculator;

public class Assert_All {
	@Test
	void groupedAssertions() {
	        // In a grouped assertion all assertions are executed, and all failures will be reported together.
		Assertions.assertAll("person", 
	        		 () -> Assertions.assertEquals("Jane", "Jane1", "First names missmatch"),
	        		 () -> Assertions.assertEquals("Doe", "Doe1", "Second names missmatch")
	        );
	}
	
    @Test
    void dependentAssertions() {
        // Within a code block, if an assertion fails the subsequent code in the same block will be skipped.
    	Assertions.assertAll("properties",
            () -> {
                String firstName = "Jane"; // person.getFirstName();
                Assertions.assertNotNull(firstName);

                // Executed only if the previous assertion is valid.
                Assertions.assertAll("first name",
                    () -> Assertions.assertTrue(firstName.startsWith("J")),
                    () -> Assertions.assertTrue(firstName.endsWith("e"))
                );
            },
            () -> {
                // Grouped assertion, so processed independently
                // of results of first name assertions.
                String lastName = "Doe"; // person.getLastName();
                Assertions.assertNotNull(lastName);

                // Executed only if the previous assertion is valid.
                Assertions.assertAll("last name",
                    () -> Assertions.assertTrue(lastName.startsWith("D")),
                    () -> Assertions.assertTrue(lastName.endsWith("e"))
                );
            }
        );
    }
}
