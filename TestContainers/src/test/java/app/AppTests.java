package app;

import org.junit.jupiter.api.*;

public class AppTests {

    private App app;

    @BeforeEach
    public void initTest() {
        app = new App();
    }

    @AfterEach
    public void afterTest() {
        app = null;
    }

    @Test
    public void checkInfo() throws Exception {
        app.printInfo();
    }
}