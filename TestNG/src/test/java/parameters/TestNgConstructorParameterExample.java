package parameters;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
 
public class TestNgConstructorParameterExample {
	private String value = "";
	
    @Parameters("constructor_parameter")
    public TestNgConstructorParameterExample(String param) {
        System.out.println("TestNgConstructorParameterExample(" + param + ")");
        value = param;
    }
 
    @Test
    public void Test() {
        System.out.println("Test method: param = " + this.value);
    }
}