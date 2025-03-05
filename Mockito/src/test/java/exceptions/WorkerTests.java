package exceptions;

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
public class WorkerTests
{
    @Mock
    Service service;

    @InjectMocks
    Worker worker;

    @Test
    public void testOK()
    {
        Mockito.when(service.getMessage()).thenReturn("Some Time");
        String response = worker.callService();
        assertThat(response).isEqualTo("Success: Some Time");
    }

    @Test
    public void testExc()
    {
        Mockito.when(service.getMessage()).thenThrow(new RuntimeException("Ops"));
        String response = worker.callService();
        assertThat(response).isEqualTo("Failure: Ops");
    }

    @Test
    public void testCallMockWithParams()
    {
        // ArgumentCaptor<String> param = ArgumentCaptor.forClass(String.class);
        // Mockito.when(service.getMessageWithParam(param.capture())).thenReturn(param.getValue());

        // Mockito.when(service.getMessageWithParam(anyString())).thenReturn("3434");

        Mockito.when(service.getMessageWithParam(anyString())).thenAnswer(
                input -> "[" + input.getArgument(0) + "]"
        );

        String response = worker.callServiceWithParam("PREFIX");
        assertThat(response).isEqualTo("PREFIX : 3434");
    }
}
