package lesson1;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

/*

Допустим, нужно, чтобы при тестировании метод getPower() возвращал иное значение — 500.
Чтобы не менять код другого класса, можно создать стаб и написать так:
     Mockito.when(engine.getPower()).thenReturn(500).
То есть «когда вызван метод getPower(), верни значение 500».

*/

@RunWith(MockitoJUnitRunner.class)
public class LessonOneTest {

    @Mock
    Engine engine; // создали стаб

    @Test
    public void test() {
        Car car = new Car(engine);
        System.out.println(car.getEnginePower()); // аннотация @Mock стирает возвращаемые значения, поэтому выведется 0
        Mockito.when(engine.getPower()).thenReturn(500);
        // теперь при вызове getPower() всегда будет возвращаться 500
        System.out.println(car.getEnginePower()); // выведется 500
    }
}