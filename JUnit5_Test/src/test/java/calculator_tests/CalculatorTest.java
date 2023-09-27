package calculator_tests;

import Utilities.Calculator;
import org.junit.jupiter.api.Assertions;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.AfterEach;

public class CalculatorTest {

    private Calculator calculator;

    @BeforeAll
    public static void beforeClass() {
        System.out.println("Before CalculatorTest.class");
    }

    @AfterAll
    public  static void afterClass() {
        System.out.println("After CalculatorTest.class");
    }

    @BeforeEach
    public void initTest() {
        calculator = new Calculator();
    }

    @AfterEach
    public void afterTest() {
        calculator = null;
    }

    @Test
    public void testGetSum() throws Exception {
    	Assertions.assertEquals(15, calculator.add(7,8));
    }

    @Test
    public void testGetDivide() throws Exception {
    	Assertions.assertEquals(20, calculator.divide(100,5));
    }

    @Test
    public void testGetMultiple() throws Exception {

    }

    @Test
    public void divisionWithException() {
    	@SuppressWarnings("unused")
		Exception exception1 = Assertions.assertThrows(ArithmeticException.class, () -> {
           calculator.divide(15,0);
        });
    	
    	Exception exception2 = Assertions.assertThrows(NumberFormatException.class, () -> {
    		 Integer.parseInt("ze");
        });
    }

    @Disabled("Message for ignored test")
    @Test
    public void ignoredTest() {
        System.out.println("will not print it");
    }

    @Disabled("Message for ignored test")
    @Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
    @Test
    public void timeStampTest() {
        while (true);
    }
}