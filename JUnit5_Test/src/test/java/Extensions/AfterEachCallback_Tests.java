package Extensions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;

@ExtendWith(AfterEachCallbackImpl.class)
public class AfterEachCallback_Tests {
    @Test
    void testCase1() {
        Assumptions.assumeTrue(true);
    }

    @Test
    void testCase2() {
        Assertions.assertTrue(true);
    }

    @Test
    void testCase3() {
        Assertions.assertTrue(false);
    }
}


class AfterEachCallbackImpl implements AfterEachCallback {

    @Override
    public void afterEach(ExtensionContext extensionContext) throws Exception {
        System.out.println("EXECUTED AFTER: " + extensionContext.getDisplayName());
    }
}