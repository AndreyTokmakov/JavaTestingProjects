package Spy;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.Mockito;
import org.mockito.Spy;

import org.junit.Assert;

@RunWith(MockitoJUnitRunner.class)
public class CircleTest {

    @Spy
    private Circle circle;

    @Test
    public void getColourBlue() {
        circle.setColour(10);
        Assert.assertEquals(Color.BLUE, circle.getColour());
    }

    @Test
    public void getColourRed() {
        circle.setColour(3);
        Assert.assertEquals(Color.RED, circle.getColour());
    }

    @Test
    public void getColourNoParameters() {
        circle.setColour();
        Mockito.verify(circle, Mockito.times(1)).setColour(1);
    }
}