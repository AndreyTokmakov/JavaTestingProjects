package lesson2;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

/*
Можно создать стабы и для объектов, у которых есть методы с параметрами.
Например, метод getWheelsCount() принимает количество передних и задних колёс, а возвращает их сумму:

В коде стаба нужно указать, при каких аргументах метод должен возвращать значение.
Например, стаб будет выводить 5, только если передали аргументы 2 и 2. Иначе — 0 по умолчанию:

*/

@RunWith(MockitoJUnitRunner.class)
public class LessonTwoTest2 {

    @Mock
    Wheel wheel;

    @Test
    public void test() {
        Car car = new Car(wheel);
        Mockito.when(wheel.countWheels(2, 2)).thenReturn(5);
        System.out.println(car.getWheelsCount(2,2)); // выведется 5
    }
}