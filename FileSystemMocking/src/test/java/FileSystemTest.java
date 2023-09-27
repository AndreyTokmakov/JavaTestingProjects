
import com.github.marschall.memoryfilesystem.MemoryFileSystemBuilder;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

public class FileSystemTest {

    @Test
    public void fileSystemTest() throws IOException
    {
        // TODO: This is use 'the real FileSystem'
        // Files.write(Path.of("/home/andtokm/DiskS/Temp/TESTING_ROOT_DIR/test.txt"), "Hello.txt".getBytes());


        // TODO: This is use 'the MemoryFileSystem'
        try (FileSystem fileSystem = MemoryFileSystemBuilder.newEmpty().build()) {
            Path txt = fileSystem.getPath("test.txt");
            System.out.println(String.format("Path: %s", txt));

            Files.write(txt, "Hello World".getBytes());

            assertThat(Files.exists(txt)).isTrue();
            assertThat(Files.readString(txt)).isEqualTo("Hello World");
        }
    }
}