package lesson3;

import org.junit.Test;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

/*
Стаб чтобы метод возвращал 80000: не важно, какие именно значения для hours, rate и bonus в него передали.
*/

@RunWith(MockitoJUnitRunner.class)
public class LessonThreeTestTwo {

    @Mock
    PayrollCalculator payrollCalculator;

    @Test
    public void calcSalaryTest() {

        Mockito.when(payrollCalculator
                        .calcSalary(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt()))
                .thenReturn(80000);

        // Mockito.when(payrollCalculator.calcSalary(40, 90, 0)).thenReturn(80000);
        // Mockito.when(payrollCalculator.calcSalary(40, 90, 10)).thenReturn(80000);

        Assert.assertEquals(80000, payrollCalculator.calcSalary(40, 90, 0));
        Assert.assertEquals(80000, payrollCalculator.calcSalary(40, 90, 10));
    }
}