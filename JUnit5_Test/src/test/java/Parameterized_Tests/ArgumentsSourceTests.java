package Parameterized_Tests;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.api.Assertions;

import java.util.stream.Stream;

class CustomArgumentsProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments>
		provideArguments(ExtensionContext extensionContext) throws Exception {
        return Stream.of("java", "rust", "kotlin").map(Arguments::of);
    }
}


class CustomArgumentsProviderMap implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments>
		provideArguments(ExtensionContext extensionContext) throws Exception {
        return Stream.of("java", "rust", "kotlin").map(Arguments::of);
    }
}

public class ArgumentsSourceTests {
	@ParameterizedTest
    @ArgumentsSource(CustomArgumentsProvider.class)
    void test_argument_custom(String arg) {
		Assertions.assertNotNull(arg);
    }

}
