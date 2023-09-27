package userService;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MailServiceTestNoMock {
    @Test
    public void mailTest() {
        UserService userService = new UserService(new MailService());
        boolean userRegistered = userService.register(new UserDto("Marco", 99, "marco@jetbrains.com"));
        assertThat(userRegistered).isTrue();
    }
}
