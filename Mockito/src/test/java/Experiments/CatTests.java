package Experiments;

import lesson1.Car;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.Spy;

@RunWith(MockitoJUnitRunner.class)
public class CatTests {
    @Spy
    public Cat cat = new Cat("Пушок");

    @Test
    public void test() {

    }
}

class Cat {
    private String name;

    public Cat(String name){
        this.name = name;
    }
}