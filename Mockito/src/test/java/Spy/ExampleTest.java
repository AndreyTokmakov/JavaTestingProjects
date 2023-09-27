package Spy;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.Spy;

@RunWith(MockitoJUnitRunner.class)//раннер
public class ExampleTest{
    @Spy
    private Example example = new Example(3);


    @Test
    public void getIdTest() {
        Assert.assertEquals(3, example.getId());
    }
}