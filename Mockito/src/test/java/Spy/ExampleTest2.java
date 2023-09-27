package Spy;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ExampleTest2 {
    @Test
    public void getIdTest() {
        Example example = new Example(3); // created an object
        Example exampleSpy  = Mockito.spy(example); // created a spy object
        Assert.assertEquals(3, exampleSpy.getId());
    }
}