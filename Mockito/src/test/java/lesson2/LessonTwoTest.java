package lesson2;

import org.junit.Test;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

/*
Создай стаб для объекта класса List, который при вызове метода size() возвращает значение 100.
Используй аннотацию @Mock.
*/
@RunWith(MockitoJUnitRunner.class)
public class LessonTwoTest {
    @Mock
    List<String> list;

    @Test
    public void friendsTest() {
        Friend friend = new Friend(list);
        Mockito.when(list.size()).thenReturn(100);
        Assert.assertEquals(100, friend.getFriendsCount());
    }
}