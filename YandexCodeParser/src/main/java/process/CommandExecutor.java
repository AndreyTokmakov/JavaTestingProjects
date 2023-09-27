package process;


import filesystem.FileSystem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

public class CommandExecutor {
    private static final Logger log = LogManager.getLogger(CommandExecutor.class.getName());

    /**
     * From Java 11 you can run code with compiling "on the fly"
     *
     * @param path - Path to source .java file
     * @return [ProcessDecorator], that wraps around process
     */
    public static ProcessDecorator runJavaCode(Path path) {
        if (!path.toFile().exists())
            System.exit(1);

        var command = "java " + path.toString();
        log.trace("Compiling and running code: '{}'", command);

        try {
            var code = FileSystem.readTextFromFile(path);
            log.trace("{}{}", System.lineSeparator(), code);
        } catch (IOException e) {
            log.trace(e);
        }

        var process = execCommand(command, 15L, TimeUnit.SECONDS);

        return new ProcessDecorator(process);
    }

    public static Process execCommand(String command, Long timeout, TimeUnit timeUnit) {
        Process process = null;

        try {
            process = Runtime.getRuntime().exec(command);
            process.waitFor(timeout, timeUnit);
        } catch (InterruptedException e) {
            log.error("Программа выполняется дольше {} секунд", timeout);
            log.debug(e);
        } catch (IOException e) {
            log.error("Непредвиденная ошибка при компиляции / выполнении программы");
            log.debug(e);
        }

        return process;
    }
}
