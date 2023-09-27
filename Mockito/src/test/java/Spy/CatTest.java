package Spy;

import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.Spy;


@RunWith(MockitoJUnitRunner.class)
public class CatTest{
    @Spy
    Cat cat = new Cat("Пушок");
}