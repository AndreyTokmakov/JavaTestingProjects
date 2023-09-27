package Extensions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import java.util.Optional;

@ExtendWith(TestStatusesWatcherImpl.class)
public class TestStatusesWatcher {
    @Test
    void testCase1() {
        Assumptions.assumeTrue(true);
    }

    @Disabled
    @Test
    void testCase2() {
        Assertions.assertTrue(true);
    }

    @Test
    void testCase3() {
        Assumptions.assumeTrue(false);
    }
}


class TestStatusesWatcherImpl implements TestWatcher {
    @Override
    public void testDisabled(ExtensionContext context, Optional<String> reason) {
        System.out.println("TEST DISABLED: " + context.getDisplayName() );
    }

    @Override
    public void testSuccessful(ExtensionContext context) {
        System.out.println("TEST SUCCEEDED: " + context.getDisplayName() );
    }

    @Override
    public void testAborted(ExtensionContext context, Throwable cause) {
        System.out.println("TEST ABORTED: " + context.getDisplayName() );
        TestWatcher.super.testAborted(context, cause);
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        System.out.println("TEST FAILED: " + context.getDisplayName() );
        TestWatcher.super.testFailed(context, cause);
    }
}