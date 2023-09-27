package nats;

import io.nats.client.Connection;
import io.nats.client.Dispatcher;
import io.nats.client.Nats;
import io.nats.client.Options;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

import java.io.IOException;
import java.time.Duration;
import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

public class NatsContainerTest {
    public static final Integer NATS_PORT = 4222;
    public static final Integer NATS_MGMT_PORT = 8222;
    public static final String imageVersion = "nats:2.9.8-alpine3.16";

    private static GenericContainer<?> nats = null;

    @Container
    public GenericContainer redis = new GenericContainer(DockerImageName.parse("redis:5.0.3-alpine"))
            .withExposedPorts(6379);


    private void sleep(int msSeconds) {
        try {
            TimeUnit.MILLISECONDS.sleep(msSeconds);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @BeforeAll
    public static void beforeClass() {
        nats = new GenericContainer<>(imageVersion).withExposedPorts(NATS_PORT, NATS_MGMT_PORT);
        nats.start();
    }

    @AfterAll
    public  static void afterClass() {
        nats.close();
    }

    /*
    @BeforeEach
    public void initTest() {
        nats = new GenericContainer<>(imageVersion).withExposedPorts(NATS_PORT, NATS_MGMT_PORT);
        nats.start();
    }

    @AfterEach
    public void afterTest() {
        nats.close();
    }
    */

    @Test
    @Disabled
    void test() throws IOException, InterruptedException
    {
        try (final Connection connection = Nats.connect(new Options.Builder().server(
                "nats://" + nats.getHost() + ":" + nats.getMappedPort(NATS_PORT)).build()))
        {
                assertThat(connection.getStatus()).isEqualTo(Connection.Status.CONNECTED);
        }
    }

    @Test
    @Disabled
    void testServerStatus() throws IOException
    {
        HttpUriRequest request = new HttpGet(String.format(
                "http://%s:%d/varz", nats.getHost(), nats.getMappedPort(NATS_MGMT_PORT)));
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        assertThat(httpResponse.getStatusLine().getStatusCode()).isEqualTo(HttpStatus.SC_OK);
    }

    @Test
    @Disabled
    void publishAndSubscribeTest() throws IOException, InterruptedException
    {
        final String channelName = "nats.demo.service";
        try (final Connection connection = Nats.connect(new Options.Builder().server(
                "nats://" + nats.getHost() + ":" + nats.getMappedPort(NATS_PORT)).build()))
        {
            final CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                final Dispatcher dispatcher = connection.createDispatcher(msg -> {});
                dispatcher.subscribe(channelName, msg -> {
                    System.out.println("Received : " + new String(msg.getData()));
                });
                System.out.println("Done");
            });

            sleep(1_000);
            // publish a message to the channel
            connection.publish(channelName, "Hello NATS".getBytes());
        }
    }

    @Test
    @Disabled
    void publishAndSubscribeClientTest() throws IOException, InterruptedException
    {
        final String channelName = "nats.demo.service";
        try (final Connection connection = Nats.connect(new Options.Builder().server(
                "nats://" + nats.getHost() + ":" + nats.getMappedPort(NATS_PORT)).build()))
        {

            final CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                sleep(1_000);
                // publish a message to the channel
                connection.publish(channelName, "Hello NATS".getBytes());
            });


            NatsConsumer consumer = new NatsConsumer("localhost", nats.getMappedPort(NATS_PORT));
            String msg = consumer.getMessage("nats.demo.service", Duration.ofMillis(10_000));

            System.out.println("=".repeat(100));
            System.out.println(msg);
            System.out.println("=".repeat(100));

        }
    }

    @Test
    void checkServerInfo() throws IOException, InterruptedException
    {
        final String channelName = "nats.demo.service";
        final String message = "Data_To_Send";

        try (final Connection connection = Nats.connect(new Options.Builder().server(
                "nats://" + nats.getHost() + ":" + nats.getMappedPort(NATS_PORT)).build())) {

            System.out.println(connection.getServerInfo());
        }
    }

    @Test
    void publishAndSubscribeClientTest2() throws IOException, InterruptedException
    {
        final String channelName = "nats.demo.service";
        final String message = "Data_To_Send";

        try (final Connection connection = Nats.connect(new Options.Builder().server(
                "nats://" + nats.getHost() + ":" + nats.getMappedPort(NATS_PORT)).build())) {

            final CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                NatsProducer.publish(channelName, message, connection);
            });

            String msg = NatsConsumer.getMessage("nats.demo.service", Duration.ofMillis(3_000), connection);
            Assertions.assertEquals(message, msg, "Published and Received message shall be equal");
        }
    }

    @Test
    void publishAndSubscribeClientTest_Error() throws IOException, InterruptedException
    {
        final String channelName = "nats.demo.service";
        final String message = "Data_To_Send";

        try (final Connection connection = Nats.connect(new Options.Builder().server(
                "nats://" + nats.getHost() + ":" + nats.getMappedPort(NATS_PORT)).build())) {

            final CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                NatsProducer.publish(channelName, message, connection);
                NatsProducer.publish(channelName, message, connection);
            });

            String msg = NatsConsumer.getMessage("nats.demo.service", Duration.ofMillis(3_000), connection);
            Assertions.assertEquals(message, msg + "_Error", "Published and Received message shall be equal");
        }
    }
}
