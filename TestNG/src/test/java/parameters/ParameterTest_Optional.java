package parameters;

import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
 
public class ParameterTest_Optional	
{
    @Parameters({ "optional-value" })
    @Test
    public void optionTest(@Optional("OPTIONAL_VALUE") 
    					   final String value) {
        System.out.println("This is: " + value);
    }
}