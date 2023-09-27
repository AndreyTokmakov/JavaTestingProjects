package nats;

import io.nats.client.Connection;
import io.nats.client.Nats;
import io.nats.client.Options;
import io.nats.client.api.ServerInfo;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;



interface INatSTestService
{
    final static Integer PORT = 4222;
    final static String HOST = "localhost";

    public Connection getConnection() throws IOException, InterruptedException;

    public int getNatsPort();
}

class RealNASService implements INatSTestService
{
    @Override
    public Connection getConnection() throws IOException, InterruptedException {
        return Nats.connect(String.format("nats://%s:%d", HOST, PORT));
    }

    @Override
    public int getNatsPort()
    {
        return PORT;
    }
}

class TestContainersNASService implements INatSTestService
{
    public static final String imageVersion = "nats:2.9.8-alpine3.16";
    private static GenericContainer<?> nats = null;

    @BeforeAll
    public static void beforeClass() {
        nats = new GenericContainer<>(imageVersion).withExposedPorts(PORT);
        nats.start();
    }

    @AfterAll
    public  static void afterClass() {
        nats.close();
    }

    @Override
    public Connection getConnection() throws IOException, InterruptedException {
        return Nats.connect(new Options.Builder().server(
                "nats://" + nats.getHost() + ":" + nats.getMappedPort(PORT)).build());
    }
    @Override
    public int getNatsPort()
    {
        return nats.getMappedPort(PORT);
    }
}



public class DemoNASTTests extends TestContainersNASService
{
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
        try (final Connection connection = getConnection())
        {
            Assertions.assertEquals(Connection.Status.CONNECTED , connection.getStatus(),
                    "Connection shall be established");
        }
    }

    @Test
    public void checkServerHostAndPort() throws Exception
    {
        try (final Connection connection = getConnection())
        {
            final ServerInfo info = connection.getServerInfo();
            Assertions.assertEquals("0.0.0.0", info.getHost(), "Host should be equal to 0.0.0.0");
            Assertions.assertEquals(PORT, info.getPort(), "Port should be equal to 4222");
        }
    }

    @Test
    public void publishSubscribeSingleMessage() throws Exception
    {
        final String messageExpected = "Hello from NATS";

        CompletableFuture<Optional<String>> consumer = CompletableFuture.supplyAsync(() -> {
            NatsConsumer natsConsumer = new NatsConsumer(HOST, getNatsPort());
            return natsConsumer.getMessageNoexcept(channel, Duration.ofMillis(5_000));
        });

        CompletableFuture<Void> publisher = CompletableFuture.runAsync(() -> {
            NatsProducer producer = new NatsProducer(HOST, getNatsPort());
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
            NatsConsumer natsConsumer = new NatsConsumer(HOST, getNatsPort());
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
            NatsProducer producer = new NatsProducer(HOST, getNatsPort());
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
