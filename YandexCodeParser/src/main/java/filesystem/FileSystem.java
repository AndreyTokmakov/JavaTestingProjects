package filesystem;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class FileSystem {
    public static String readTextFromFile(Path path) throws IOException {
        return String.join(System.lineSeparator(), Files.readAllLines(path));
    }

    public static void writeTextToFile(String text, File file) throws IOException {
        try (final BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(text);
        }
    }

    public static Path getExecutionDirPath() {
        return Paths.get(System.getProperty("user.dir")).toAbsolutePath();
    }
}
