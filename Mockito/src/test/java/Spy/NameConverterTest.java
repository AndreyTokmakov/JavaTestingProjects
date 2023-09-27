package Spy;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.Mockito;
import org.mockito.Spy;



@RunWith(MockitoJUnitRunner.class)
public class NameConverterTest {

    @Spy
    private NameConverter nameConverter;

    @Test
    public void getFullNameByName() {
        nameConverter.getFullNameByName("Тамара");
        Mockito.verify(nameConverter,
                 Mockito.times(1)).getFullName("Тамара", null);
    }

    @Test
    public void getFullNameBySurname() {
        nameConverter.getFullNameBySurname("Петрова");
        Mockito.verify(nameConverter,
                 Mockito.times(1)).getFullName(null, "Петрова");
    }

}