package mockReturnValue_BasedOnInput;

import exceptions.Service;
import exceptions.Worker;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.anyString;

@RunWith(MockitoJUnitRunner.class)
public class Mock_Return_Bases_On_Input
{
    @Mock
    Service service;

    @InjectMocks
    Worker worker;

    @Test
    public void testCallMockWithParams()
    {
        Mockito.when(service.getMessageWithParam(anyString())).thenAnswer(
                input -> "[" + input.getArgument(0) + "]"
        );

        String response = worker.callServiceWithParam("PREFIX");
        assertThat(response).isEqualTo("PREFIX : [PREFIX]");
    }
}
