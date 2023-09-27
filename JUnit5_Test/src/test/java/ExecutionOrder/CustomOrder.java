package ExecutionOrder;

import org.junit.jupiter.api.MethodDescriptor;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.MethodOrdererContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Comparator;
import java.math.BigDecimal;

class ParameterCountOrder implements MethodOrderer {

    private Comparator<MethodDescriptor> comparator = Comparator.comparingInt(md1 -> md1.getMethod().getParameterCount());

    @Override
    public void orderMethods(MethodOrdererContext context) {
        context.getMethodDescriptors().sort(comparator.reversed());

    }
}

@TestMethodOrder(ParameterCountOrder.class)
public class CustomOrder {

    @DisplayName("Parameter Count : 2")
    @ParameterizedTest(name = "{index} ==> fruit=''{0}'', qty={1}")
    @CsvSource({
            "apple,         1",
            "banana,        2"
    })
    void test2(String fruit, int qty) {
    	Assertions.assertTrue(true);
    }

    @DisplayName("Parameter Count : 1")
    @ParameterizedTest(name = "{index} ==> ints={0}")
    @ValueSource(ints = {1, 2, 3})
    void test1(int num1) {
    	Assertions.assertTrue(num1 < 4);
    }

    @DisplayName("Parameter Count : 3")
    @ParameterizedTest(name = "{index} ==> fruit=''{0}'', qty={1}, price={2}")
    @CsvSource({
            "apple,         1,  1.99",
            "banana,        2,  2.99"
    })
    void test3(String fruit, int qty, BigDecimal price) {
    	Assertions.assertTrue(true);
    }

}