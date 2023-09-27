package lesson3;

import org.junit.Test;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

/*
Стаб для PayrollCalculator, который при вызове метода calcSalary() со значениями 0, 0, 0 будет возвращать 50000.
Стаб для PayrollCalculator, который при вызове метода calcSalary() со значениями 10, 10, 10 будет возвращать 110.
*/

@RunWith(MockitoJUnitRunner.class)
public class LessonThreeTest {

    @Mock
    PayrollCalculator payrollCalculator;

    @Test
    public void calcSalaryTest() {
        Mockito.when(payrollCalculator.calcSalary(0, 0, 0)).thenReturn(50000);
        Mockito.when(payrollCalculator.calcSalary(10, 10, 10)).thenReturn(110);

        Assert.assertEquals(50000, payrollCalculator.calcSalary(0, 0, 0));
        Assert.assertEquals(110, payrollCalculator.calcSalary(10, 10, 10));
    }

}