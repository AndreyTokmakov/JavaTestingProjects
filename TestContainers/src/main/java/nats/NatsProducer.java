package nats;

import io.nats.client.Connection;
import io.nats.client.Message;
import io.nats.client.Nats;
import io.nats.client.Subscription;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

public class NatsProducer
{
    private final String connectString;

    public NatsProducer(String host, int port)
    {
        this.connectString = String.format("nats://%s:%d", host, port);
    }

    public void publish(String channelName,
                        String message) throws IOException, InterruptedException
    {
        try (final Connection connection = Nats.connect(this.connectString))
        {
            connection.publish(channelName, message.getBytes());
        }
    }

    public void publishNoexcept(String channelName,
                                String message)
    {
        try (final Connection connection = Nats.connect(this.connectString))
        {
            connection.publish(channelName, message.getBytes());
        }
        catch (Exception exc) {
            System.err.println(exc.getMessage());
        }
    }

    public static void publish(String channelName,
                               String message,
                               Connection exisingConnection)
    {
        exisingConnection.publish(channelName, message.getBytes());
    }
}
