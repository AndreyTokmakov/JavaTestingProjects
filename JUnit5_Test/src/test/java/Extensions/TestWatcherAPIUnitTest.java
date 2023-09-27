package Extensions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@ExtendWith(TestResultLoggerExtension.class)
class TestWatcherAPIUnitTest {

    @Test
    void testCase1() {
        Assumptions.assumeTrue(true);
    }

    @Disabled
    @Test
    void testCase2() {
    	Assertions.assertTrue(true);
    }

    @Disabled
    @Test
    void testCase3() {
        Assumptions.assumeTrue(true);
    }

    @Disabled("This test is disabled")
    @Test
    void testCase4() {
    	Assertions.fail("Not yet implemented");
    }
}

class TestResultLoggerExtension implements TestWatcher, AfterAllCallback {
    // private static final Logger LOG = LoggerFactory.getLogger(TestResultLoggerExtension.class);
    private final List<TestResultStatus> testResultsStatus = new ArrayList<>();

    private enum TestResultStatus {
        SUCCESSFUL, ABORTED, FAILED, DISABLED;
    }

    @Override
    public void testDisabled(ExtensionContext context, Optional<String> reason) {
        // LOG.info("Test Disabled for test {}: with reason :- {}", context.getDisplayName(), reason.orElse("No reason"));
        testResultsStatus.add(TestResultStatus.DISABLED);
        System.out.println("TEST DISABLED: " + context.getDisplayName() );
    }

    @Override
    public void testSuccessful(ExtensionContext context) {
        // LOG.info("Test Successful for test {}: ", context.getDisplayName());
        System.out.println("TEST SUCCEEDED: " + context.getDisplayName() );
        testResultsStatus.add(TestResultStatus.SUCCESSFUL);
    }

    @Override
    public void testAborted(ExtensionContext context, Throwable cause) {
        // LOG.info("Test Aborted for test {}: ", context.getDisplayName());
        testResultsStatus.add(TestResultStatus.ABORTED);
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        // LOG.info("Test Failed for test {}: ", context.getDisplayName());
        testResultsStatus.add(TestResultStatus.FAILED);
    }


    @Override
    public void afterAll(ExtensionContext context) throws Exception {
        Map<TestResultStatus, Long> summary = testResultsStatus.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        // LOG.info("Test result summary for {} {}", context.getDisplayName(), summary.toString());
    }
}

