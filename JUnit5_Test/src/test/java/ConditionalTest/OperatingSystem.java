package ConditionalTest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

public class OperatingSystem {

    @Test
    @EnabledOnOs({OS.LINUX, OS.WINDOWS})
    void onLinuxOrWindows() {
        System.out.println("Run this on Linux or Windows!");
    }

    @Test
    @EnabledOnOs({OS.WINDOWS})
    void onWindows() {
        System.out.println("Run this on Windows!");
    }

    @Test
    @DisabledOnOs(OS.WINDOWS)
    void notOnWindows() {
        System.out.println("Do not run this on Windows!");
    }

    @Test
    @DisabledOnOs({OS.WINDOWS, OS.AIX, OS.SOLARIS, OS.MAC})
    @DisplayName("SOME_NAME")
    void notOnWindowsOrAixOrSolarisOrMac() {
        System.out.println("Do not run this on Windows, AIX, Solaris or MAC!");
    }

}