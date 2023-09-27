package Asserts;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;


class Point {
    private double x;
    private double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() { return x; }
    public double getY() { return y; }

    public void update(double newX, double newY) {
        x = newX;
        y = newY;
    }
}


public class AssertAll_PointOne
{
    @Test
    public void pointTest() {
        Point point  = new Point(15, 20);
        point.update(30, 14);
        assertAll(()-> assertEquals(30, point.getX()),
                ()-> assertEquals(14, point.getY())
        );
    }
}
