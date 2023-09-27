/****************************************************************************
* Copyright 2021 (C) Andrey Tokmakov
* Assert_Throw.java class
*
* @name    : Assert_Throw.java
* @author  : Tokmakov Andrey
* @version : 1.0
* @since   : Jan 9, 2021
****************************************************************************/

package Asserts;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class Assert_Throw {
	private final Utilities.Calculator calculator = new Utilities.Calculator();
	
	protected void methodUnderTest() throws CustomException {
		throw new CustomException("Expected_Text");
	}
	
	@Test
	void ArithmeticExceptionTest() {
		Exception exception = assertThrows(ArithmeticException.class, () -> calculator.divide(1, 0));
		assertEquals("/ by zero", exception.getMessage());
	}
	
	@Test
	void ArithmeticExceptionTest_SuperClass() {
		Exception exception = assertThrows(RuntimeException.class, () -> calculator.divide(1, 0));
		assertEquals("/ by zero", exception.getMessage());
	}
	
	@Test
	void CustomException_Test() {
		Exception exception = assertThrows(CustomException.class, this::methodUnderTest);
		assertEquals("Expected_Text", exception.getMessage());
	}

	@Test
	void Wrong_Exception_Expected() {
		Exception exception = assertThrows(CustomException.class, () -> calculator.divide(1, 0));
		assertEquals("/ by zero", exception.getMessage());
	}
	
    @Test
    void exceptionTesting() {
        Exception exception = Assertions.assertThrows(ArithmeticException.class, () -> calculator.divide(1, 0));
        Assertions.assertEquals("/ by zero", exception.getMessage());
    }
}

class CustomException extends Exception {
	private static final long serialVersionUID = 1L;

	public CustomException() {
		super();
	}

	public CustomException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public CustomException(String message, Throwable cause) {
		super(message, cause);
	}

	public CustomException(String message) {
		super(message);
	}

	public CustomException(Throwable cause) {
		super(cause);
	}
}
