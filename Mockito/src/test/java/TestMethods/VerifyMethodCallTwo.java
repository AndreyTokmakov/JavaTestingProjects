package TestMethods;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)// подключили Mockito к тестовому классу
public class VerifyMethodCallTwo {
    @Mock
    Comment comment;

    @Test
    public void test() {
        comment.updateText("Comment_Updated");
        Mockito.verify(comment).updateText("Comment_Updated");
    }

    @Test
    public void testCallAnyParams() {
        comment.updateText("Comment_Updated12323а");
        Mockito.verify(comment).updateText(Mockito.anyString());
    }
}
