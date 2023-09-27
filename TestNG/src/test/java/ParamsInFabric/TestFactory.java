package ParamsInFabric;

import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

class TestNgFactoryParameterExample {
	
	private String param = "";
	
	public TestNgFactoryParameterExample(String inputParam) {
		this.param = inputParam;
	}
	
	@Test
	public void TheTest() {
		System.out.println("TEST : " + this.param);
	}
}
 
public class TestFactory {
    @Factory
    @Parameters({"factory-param1", "factory-param2"})
    public Object[] create(String param1, String param2) {
        return new Object[]{new TestNgFactoryParameterExample(param1), 
        			        new TestNgFactoryParameterExample(param2) };
    }
}
