package Assumptions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;

import Utilities.User;
import Utilities.UserService;

public class AssumptionsTest {
	
    // Output: org.opentest4j.TestAbortedException: Assumption failed: assumption is not true
    @DisplayName("Run this if `assumeTrue` condition is true, else aborting this test")
    @Test
    void testOnlyOnDevEnvElseAbort() {
    	Assumptions.assumeTrue("DEV".equals(System.getenv("APP_MODE")));
        Assertions.assertEquals(2, 1 + 1);
    }

    // Output: org.opentest4j.TestAbortedException: Assumption failed: Aborting test: not on developer environment
    @DisplayName("Run this if `assumeTrue` condition is true, else aborting this test (Custom Message)")
    @Test
    void testOnlyOnDevEnvElseAbortWithCustomMsg() {
    	Assumptions.assumeTrue("DEV".equals(System.getenv("APP_MODE")), () -> "Aborting test: not on developer environment");
        Assertions.assertEquals(2, 1 + 1);
    }

    @Test
    void testAssumingThat() {

        // run these assertions always, just like normal test
    	Assertions.assertEquals(2, 1 + 1);

    	Assumptions.assumingThat("DEV".equals(System.getenv("APP_MODE")),() -> {
        	// run this only if assumingThat condition is true
    		Assertions.assertEquals(2, 1 + 1);
        });
        Assertions.assertEquals(2, 1 + 1);
    }
    
	// assumeTrue() -> Run only if environment is DEV
	@Test
    void testOnlyOnDevEnvironment() {
		Assumptions.assumeTrue("DEV".equals(System.getenv("ENV_SETUP")));

        User user = new User(1, "Peter", "peterm@email.com");
        UserService.saveOrUpdate(user);
        Assertions.assertTrue(UserService.users.get(new Long(1)) == user);
    }
	
	// assumeFalse() -> Run only if environment is not PROD
	@Test
    void testOnlyIfNotOnProdEnvironment() {
		Assumptions.assumeFalse("PROD".equals(System.getenv("ENV_SETUP")));

        User user = new User(1, "Peter", "peterm@email.com");
        UserService.saveOrUpdate(user);
        Assertions.assertTrue(UserService.users.get(new Long(1)) == user);
    }

	// assumeTrue() -> Run only if environment is DEV, if not display message
    @Test
    void testOnlyOnDeveloperWorkstation() {
    	Assumptions.assumeTrue("DEV".equals(System.getenv("ENV_SETUP")),
            () -> "Aborting test: not on developer workstation");
        
        User user = new User(1, "Peter", "peterm1@email.com");
        UserService.saveOrUpdate(user);
        Assertions.assertTrue(UserService.users.get(new Long(1)) == user);
    }

    // assumeThat()
    @Test
    void testInAllEnvironments() {
    	Assumptions.assumingThat("DEV".equals(System.getenv("ENV_SETUP")),
            () -> {
                // perform these assertions only on the DEV server
            	UserService.saveOrUpdate(new User(1, "Peter", "peterm@email.com"));
            });

        // perform these assertions in all environments
        User user = new User(1, "Peter", "peterm1@email.com");
        UserService.saveOrUpdate(user);
        Assertions.assertTrue(UserService.users.get(new Long(1)) == user);
    }
}
