package redis;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;


// KAFKA

@Testcontainers
public class RedisTestExample {

    @Container
    public GenericContainer redis = new GenericContainer(DockerImageName.parse("redis:5.0.3-alpine"))
            .withExposedPorts(6379);

    /*
    @Container
    private static final PostgreSQLContainer<?> database = new PostgreSQLContainer<>("postgres:12.9-alpine");
    */

    // private RedisBackedCache underTest;


    @BeforeEach
    public void setUp() {
        // String address = redis.getHost();
        // Integer port = redis.getFirstMappedPort();

        final String redisUrl = redis.getHost() + ":" + redis.getMappedPort(6379);

        System.out.println("=".repeat(100));
        System.out.println(redisUrl);
        System.out.println("=".repeat(100));

        // Now we have an address and port for Redis, no matter where it is running
        // underTest = new RedisBackedCache(address, port);
    }


    @Test
    public void testOne() throws Exception {
        System.out.println("testOne");
    }
}
