package Exception_Expectaions;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

class NameNotFoundException extends Exception {
	private static final long serialVersionUID = 4979217800971960564L;

	public NameNotFoundException(String message) {
        super(message);
    }
}

public class UncheckedException {

    String findByName(String name) throws NameNotFoundException{
        throw new NameNotFoundException( name + " not found!");
    }

    int divide(int input, int divide) {
        return input / divide;
    }
    
    ////////////////////////////////////////////////
    
    @Test
    void test_exception() {
        Exception exception = Assertions.assertThrows(ArithmeticException.class,() -> divide(1, 0));
        Assertions.assertEquals("/ by zero", exception.getMessage());
        Assertions.assertTrue(exception.getMessage().contains("zero"));
    }
    
    @Test
    void test_exception_custom() {
        Exception exception = Assertions.assertThrows(NameNotFoundException.class,() -> findByName("mkyong"));
        Assertions.assertTrue(exception.getMessage().contains("not found"));
    }
}
