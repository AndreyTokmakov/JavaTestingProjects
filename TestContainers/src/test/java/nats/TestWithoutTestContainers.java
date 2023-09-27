package nats;

import io.nats.client.Connection;
import io.nats.client.Dispatcher;
import io.nats.client.Nats;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.AfterEach;


public class TestWithoutTestContainers
{
    private final static Integer PORT = 4222;
    private final static String HOST = "localhost";
    private final static String channel = "nats.demo.service";

    protected static void sleepMilliseconds(int mSeconds)
    {
        try {
            TimeUnit.MILLISECONDS.sleep(mSeconds);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void checkConnectionStatus() throws Exception
    {
        try (final Connection connection = Nats.connect(String.format("nats://%s:%d", HOST, PORT)))
        {
            Assertions.assertEquals(Connection.Status.CONNECTED , connection.getStatus(),
                    "Connection shall be established");
        }
    }

    @Test
    public void checkServerHost() throws Exception
    {
        try (final Connection connection = Nats.connect(String.format("nats://%s:%d", HOST, PORT)))
        {
            Assertions.assertEquals(Connection.Status.CONNECTED , connection.getStatus(),
                    "Connection shall be established");
        }
    }

    @Test
    public void publishSubscribeSingleMessage() throws Exception
    {
        final String messageExpected = "Hello from NATS";

        CompletableFuture<Optional<String>> consumer = CompletableFuture.supplyAsync(() -> {
            NatsConsumer natsConsumer = new NatsConsumer(HOST, PORT);
            return natsConsumer.getMessageNoexcept(channel, Duration.ofMillis(5_000));
        });

        CompletableFuture<Void> publisher = CompletableFuture.runAsync(() -> {
            NatsProducer producer = new NatsProducer(HOST, PORT);
            sleepMilliseconds(250);
            producer.publishNoexcept(channel, messageExpected);
        });


        publisher.get();
        final Optional<String> messageActual = consumer.get();

        Assertions.assertTrue(messageActual.isPresent(), "Expecting to have something");
        Assertions.assertEquals(messageExpected, messageActual.get(), "Published and Received message shall be equal");
    }

    @Test
    public void publishSubscribeMultipleMessages() throws Exception
    {
        final int count = 10;

        CompletableFuture<List<String>> consumer = CompletableFuture.supplyAsync(() -> {
            NatsConsumer natsConsumer = new NatsConsumer(HOST, PORT);
            final List<String> messages = new ArrayList<String>();
            for (int i = 0; i < count; ++i) {
                Optional<String> msg = natsConsumer.getMessageNoexcept(channel, Duration.ofMillis(1_000));
                if (msg.isEmpty())
                    break;
                messages.add(msg.get());
            }
            return messages;
        });

        CompletableFuture<Void> publisher = CompletableFuture.runAsync(() -> {
            NatsProducer producer = new NatsProducer(HOST, PORT);
            for (int i = 0; i <= count; ++i) {
                sleepMilliseconds(10);
                producer.publishNoexcept(channel, "Hello from NATS");
            }
        });

        publisher.get();
        final List<String> messages = consumer.get();
        System.out.println(messages);

        Assertions.assertEquals(count, messages.size(),
                "Expecting to receive 10 messages");
    }
}
