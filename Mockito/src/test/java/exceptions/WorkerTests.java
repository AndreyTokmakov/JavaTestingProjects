package exceptions;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;

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
}
