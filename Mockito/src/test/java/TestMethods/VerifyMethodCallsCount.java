package TestMethods;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)// подключили Mockito к тестовому классу
public class VerifyMethodCallsCount {
    @Mock
    Comment comment;

    @Test
    public void test() {
        comment.updateText("111");
        comment.updateText("222");
        comment.updateText("111");
        comment.updateText("222");
        comment.updateText("111");

        Mockito.verify(comment, Mockito.times(3)).updateText("111");
        Mockito.verify(comment, Mockito.times(2)).updateText("222");
    }
}
