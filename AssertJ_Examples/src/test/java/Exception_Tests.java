/****************************************************************************
* Copyright 2020 (C) Andrey Tokmakov
* Exception_Tests asserts demo. 
*
* @name      : Exception_Tests.java
* @author    : Tokmakov Andrey
* @version   : 1.0
* @since     : October 30, 2020
****************************************************************************/


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

// import static org.assertj.core.util.Sets.newLinkedHashSet;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;


class Worker {
	
	public static void printElement(int index) throws IndexOutOfBoundsException {
		List<String> words = new ArrayList<String>(Arrays.asList("One", "Two", "Three"));
		System.out.println(words.get(index));
	}
}

public class Exception_Tests {
	@Test
	@Disabled("for demonstration purposes") 
	public void testException() {
	   assertThatThrownBy(() -> { throw new Exception("boom!"); }).
	   				isInstanceOf(Exception.class).hasMessageContaining("boom");
	}
	
	@Test
	@Disabled("for demonstration purposes") 
	public void testException_Failed() {
	   assertThatThrownBy(() -> { throw new Exception("boom!"); }).
	   				isInstanceOf(Exception.class).hasMessageContaining("boom11");
	}
	
	@Test
	@Disabled("for demonstration purposes") 
	public void testException2() {
	   assertThatExceptionOfType(IOException.class).isThrownBy(() -> { 
		   throw new IOException("boom!"); }).withMessage("%s!", "boom").withMessageContaining("boom").withNoCause();
	}
	
	@Test
	@Disabled("for demonstration purposes") 
	public void testException2_Failed() {
	   assertThatExceptionOfType(IOException.class).isThrownBy(() -> { 
		   throw new IOException("boom!"); 
			}).withMessage("%s!", "boom").withMessageContaining("boo4m").withNoCause();
	}
	
	@Test
	public void handle_Exception() {
	   try {
		   Worker.printElement(33);
	   }
	   catch (IndexOutOfBoundsException e) {
		  assertThat(e).hasMessage("Index 33 out of bounds for length 3");
	   }
	}
	
	@Test
	public void handle_Exception_NoExt() {
	   try {
		   Worker.printElement(1);
		   // Make error if no Exeption
		   failBecauseExceptionWasNotThrown(IndexOutOfBoundsException.class);
	   }
	   catch (IndexOutOfBoundsException e) {
		  assertThat(e).hasMessage("Index 33 out of bounds for length 3");
	   }
	}
}
