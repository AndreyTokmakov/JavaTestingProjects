package ExecutionOrder;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Assertions;

@TestMethodOrder(MethodOrderer.Alphanumeric.class)
public class Alphanumeric {

    @Test
    void testZ() {
    	Assertions.assertEquals(2, 1 + 1);
    }

    @Test
    void testA() {
    	Assertions.assertEquals(2, 1 + 1);
    }

    @Test
    void testY() {
    	Assertions.assertEquals(2, 1 + 1);
    }

    @Test
    void testE() {
    	Assertions.assertEquals(2, 1 + 1);
    }

    @Test
    void testB() {
    	Assertions.assertEquals(2, 1 + 1);
    }
}
