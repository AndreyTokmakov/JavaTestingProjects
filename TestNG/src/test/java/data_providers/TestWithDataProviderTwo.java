package data_providers;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

class DataProviderClass {
    @DataProvider(name = "data-provider")
    public static Object[][] dataProviderMethod() 
    {
        return new Object[][] { { "DATA_1" }, { "DATA_2" }, { "DATA_3" }, { "DATA_4" }, { "DATA_5" }, { "DATA_6" }};
    }
}

public class TestWithDataProviderTwo {
    @Test(dataProvider = "data-provider",
    	  dataProviderClass = DataProviderClass.class)
    public void testMethod(String data) 
    {
        System.out.println("Data is: " + data);
    }
}