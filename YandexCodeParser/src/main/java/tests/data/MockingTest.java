package tests.data;

import tests.test_types.Cookie;
import tests.test_types.mocking.MockitoEx;
import tests.test_types.mocking.MyMock;
import tests.test_types.MyTest;

class PayrollCalculator {
    public int calcSalary(int hours, int rate, int bonus) {
        return hours * rate + bonus;
    }
}

public class MockingTest
{
    @MyMock
    PayrollCalculator payrollCalculator;

    @MyTest
    void test() {
        MockitoEx.when(payrollCalculator.calcSalary(0, 0, 0)).thenReturn(50000);
    }
}
