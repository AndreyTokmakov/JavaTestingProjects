package JUnit4;

import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.After;
import org.junit.Ignore;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class CheckIsAdultTest {

    private final int age;

    private final boolean result;

    public CheckIsAdultTest(int age, boolean result) {
        this.age = age;
        this.result = result;
    }

    @Parameterized.Parameters
    public static Object[][] getTextData() {
        return new Object[][] { { 16, false }, { 17, false }, { 18, true }, { 19, true }, { 21, true } };
    }

    @Test
    public void checkIsAdultWhenAgeThenResult() {
        Program program = new Program();
        boolean isAdult = program.checkIsAdult(age);
        assertEquals("Для возраста " + age + " ожидали получить " + result +
                ", а получили - " + isAdult, result, isAdult);
    }
}

class Program {

    public boolean checkIsAdult(int age) {
        return age >= 18;
    }
}