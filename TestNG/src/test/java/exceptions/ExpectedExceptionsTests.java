package exceptions;

import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.List;

public class ExpectedExceptionsTests extends Assert {
    @Test(expectedExceptions = NullPointerException.class)
    public void testNullPointerException() {
        @SuppressWarnings("rawtypes")
		List list = null;
        @SuppressWarnings("unused")
		int size = list.size();
    }
}