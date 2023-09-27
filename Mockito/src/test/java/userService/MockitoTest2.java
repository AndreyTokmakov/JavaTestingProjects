package userService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class MockitoTest2 {

    UserService userService;

    @Mock
    MailService mailService;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        userService = new UserService(mailService);
    }

    @Test
    public void test_register_user() {
        when(mailService.sendWelcomeEmail(any(UserDto.class))).thenReturn(true);
        boolean userRegistered = userService.register(new UserDto("Marco", 99, "marco@jetbrains.com"));

        assertThat(userRegistered).isTrue();
        // doThrow(RuntimeException.class).when(userService).register();
    }
}