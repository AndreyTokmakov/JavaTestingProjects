/****************************************************************************
* Copyright 2020 (C) Andrey Tokmakov
* AssertsJ examples 
*
* @name      : AssertsExamples.java
* @author    : Tokmakov Andrey
* @version   : 1.0
* @since     : October 30, 2020
* 
****************************************************************************/

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class AssertsExamples {

	@SuppressWarnings("unchecked")
	protected static <T extends Date> T getDate() {
		return (T) new Date();
	}

	@Test
    @Disabled("for demonstration purposes")
	public void assert_Date() {
	  Assertions.assertThat((Date) getDate()).isEqualTo(getDate());
	}
	
	@Test
    @Disabled("for demonstration purposes") 
	public void assert_Equal() {
		final String expected = "54321";
		final String actual = "12345";
		
		// BAD USAGE: DON'T DO THIS ! It does not assert anything
		// assertThat(actual.equals(expected));
		
		// DO THIS:
		assertThat(actual).isEqualTo(expected);

		// OR THIS (less classy but ok):
		assertThat(actual.equals(expected)).isTrue();
	}
	
	@Test
    @Disabled("for demonstration purposes") 
	public void assert_Equal_isTrue() {

		// BAD USAGE: DON'T DO THIS ! It does not assert anything and passes
		// assertThat(1 == 2);

		// DO THIS: (fails as expected)
		assertThat(1).isEqualTo(2);

		// OR THIS (less classy but ok):
		assertThat(1 == 2).isTrue();
	}
	
	@Test
    // @Disabled("for demonstration purposes") 
	public void assert_Equal_isTrue1() {
		final String expected = "ABCDE";
		final String actual = new StringBuilder(expected).reverse().toString();

		// BAD USAGE: DON'T DO THIS ! as/describedAs have no effect after the assertion
		//
		//   assertThat(actual).isEqualTo(expected).as("description");
		//
		//   assertThat(actual).isEqualTo(expected).describedAs("description");

		// DO THIS: use as/describedAs before the assertion
		assertThat(actual).as("Something went wrong 1").isEqualTo(expected);
		assertThat(actual).describedAs("description").isEqualTo(expected);
	}
}