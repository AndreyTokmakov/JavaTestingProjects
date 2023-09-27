package nats;

import io.nats.client.Connection;
import io.nats.client.Message;
import io.nats.client.Nats;
import io.nats.client.Subscription;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Optional;

public class NatsConsumer
{
    private final String connectString;

    public NatsConsumer(String host, int port)
    {
        this.connectString = String.format("nats://%s:%d", host, port);
    }

    public String getMessage(String channelName,
                             Duration duration) throws IOException, InterruptedException
    {
        try (final Connection connection = Nats.connect(this.connectString))
        {
            Subscription sub = connection.subscribe(channelName);
            Message msg = sub.nextMessage(duration);
            return new String(msg.getData(), StandardCharsets.UTF_8);
        }
    }

    public Optional<String> getMessageNoexcept(String channelName,
                                               Duration duration)
    {
        try (final Connection connection = Nats.connect(this.connectString))
        {
            Subscription sub = connection.subscribe(channelName);
            Message msg = sub.nextMessage(duration);
            if (null != msg)
                return Optional.of(new String(msg.getData(), StandardCharsets.UTF_8));
            return Optional.empty();
        } catch (IOException | InterruptedException e) {
            return Optional.empty();
        }
    }

    public static String getMessage(String channelName,
                                    Duration duration,
                                    Connection exisingConnection) throws IOException, InterruptedException
    {
        Subscription sub = exisingConnection.subscribe(channelName);
        Message msg = sub.nextMessage(duration);
        return new String(msg.getData(), StandardCharsets.UTF_8);
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        NatsConsumer consumer = new NatsConsumer("localhost", 4222);
        final String msg = consumer.getMessage("nats.demo.service", Duration.ofMillis(10_000));
        System.out.println(msg);
    }
}
