package stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

public interface OutputStreamReader {
    Logger log = LogManager.getLogger(OutputStreamReader.class.getName());

    String readOutput();

    default <T> List<T> readOutput(Function<Scanner, T> outputParser) {
        var scanner = new Scanner(readOutput());
        var outputItems = new ArrayList<T>();

        try {
            while (scanner.hasNext()) {
                var item = outputParser.apply(scanner);
                outputItems.add(item);
            }
        } catch (Exception e) {
            log.error("Ошибка чтения результатов выполнения программы");
            log.trace(e);
        }

        return outputItems;
    }
}
