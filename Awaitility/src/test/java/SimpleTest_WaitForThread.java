
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;
import static org.junit.Assert.assertThat;

public class SimpleTest_WaitForThread {
    final UserDto userDto = new UserDto("Marco", 99, "marco@jetbrains.com");

    final List<UserDto> awaitilityUserDtoList = new ArrayList<>(List.of(userDto));

    private void publishAddUserMessage(int sleepSeconds) {
        new Thread(() -> {
            try {
                Thread.sleep(sleepSeconds);
                awaitilityUserDtoList.add(new UserDto("Lisa", 99, "lisa@jetbrains.com"));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    private Callable<Boolean> newUserWasAdded() {
        return () -> awaitilityUserDtoList.size() == 2;
    }


    @Test
    public void testOK() {  // kotlin / scala / groovy DSLs available
        publishAddUserMessage(4000);
        await().atMost(5, SECONDS).until(newUserWasAdded());
        // await().atMost(5, SECONDS).untilAsserted(() -> assertThat(awaitilityUsers.size()).isEqualTo(2));
    }

    @Test
    public void testFAIL() {  // kotlin / scala / groovy DSLs available
        publishAddUserMessage(6000);
        await().atMost(5, SECONDS).until(newUserWasAdded());
        // await().atMost(5, SECONDS).untilAsserted(() -> assertThat(awaitilityUsers.size()).isEqualTo(2));
    }
}
