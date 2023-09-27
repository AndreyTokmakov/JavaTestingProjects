package ExecutionOrder;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrderAnnotation {

    @Test
    void test0() {
    	Assertions.assertEquals(2, 1 + 1);
    }

    @Test
    @Order(3)
    void test1() {
    	Assertions.assertEquals(2, 1 + 1);
    }

    @Test
    @Order(1)
    void test2() {
    	Assertions.assertEquals(2, 1 + 1);
    }

    @Test
    @Order(2)
    void test3() {
    	Assertions.assertEquals(2, 1 + 1);
    }

    @Test
    void test4() {
    	Assertions.assertEquals(2, 1 + 1);
    }
}