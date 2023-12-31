package ConditionalTest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledOnJre;
import org.junit.jupiter.api.condition.EnabledOnJre;
import org.junit.jupiter.api.condition.JRE;

public class JavaRuntimeEnvironment {

    @Test
    @EnabledOnJre(JRE.JAVA_9)
    void onJava9() {
        System.out.println("Run this on Java 9");
    }

    @Test
    @EnabledOnJre({JRE.JAVA_12, JRE.JAVA_13})
    void onJava12OrJava13() {
        System.out.println("Run this on Java 12 or Java 13");
    }

    @Test
    @DisabledOnJre(JRE.JAVA_9)
    void notOnJava9() {
        System.out.println("Do not run this on Java 9");
    }

    @Test
    @EnabledOnJre(JRE.JAVA_8)
    void onJava8() {
        System.out.println("Run this on Java 8");
    }
}