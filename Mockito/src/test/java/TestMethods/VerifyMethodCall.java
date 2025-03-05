package TestMethods;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)// подключили Mockito к тестовому классу
public class VerifyMethodCall
{
    @Mock
    Car car;

    @Test
    public void callOK() {
        car.setCarBrand("Lamborghini"); // вызвали метод объекта с аргументом
        Mockito.verify(car).setCarBrand("Lamborghini"); // проверили, что метод вызван с этим параметром
    }

    @Test
    public void wrongParam() {
        car.setCarBrand("Lamborghini"); // вызвали метод объекта с аргументом
        Mockito.verify(car).setCarBrand("Lada"); // проверили, что метод вызван с этим параметром
    }
}
