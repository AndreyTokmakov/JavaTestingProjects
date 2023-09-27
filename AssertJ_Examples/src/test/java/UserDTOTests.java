import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserDTOTests
{
    final UserDto userDto = new UserDto("Marco", 99, "marco@jetbrains.com");

    final List<UserDto> userDtoList = new ArrayList<>(List.of(userDto));


    @Test
    public void assertjTest() {

        assertEquals(userDto.name(), "Marco_Wrong");
        assertTrue(userDto.name().startsWith("Ma"));

        assertThat(userDto.name()).doesNotContainAnyWhitespaces();

        assertThat(userDtoList).hasSize(1)
                .extracting(UserDto::name)
                .doesNotContain("ocram");
    }

}
