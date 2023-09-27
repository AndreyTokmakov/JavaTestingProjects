

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import com.github.marschall.memoryfilesystem.MemoryFileSystemBuilder;

class MemoryFileSystemTester {
    /** Memory File system instance: **/
    private FileSystem fileSystem = null;
    /** Temporary file path: **/
    private final static String TEMP_FILE_NAME = "TempFile.txt";

    protected void sleep(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public MemoryFileSystemTester() throws IOException
    {
        this.fileSystem = MemoryFileSystemBuilder.newLinux()
                .setCurrentWorkingDirectory("/home/andtokm/DiskS/Temp/TESTING_ROOT_DIR")
                .build("myfs");


        System.out.println("MemoryFileSystem initialized:");
        System.out.println("   FileStores: " + fileSystem.getFileStores() );
        System.out.println("   Root dirs : " + fileSystem.getRootDirectories() + "\n");
    }

    protected boolean CreateTemporaryFile() {
        try {
            Path path = Files.createFile(fileSystem.getPath(TEMP_FILE_NAME));
            System.out.println(path.toAbsolutePath());
            return Files.exists(path);
        } catch (final IOException e) {
            e.printStackTrace();
        }
        return false;
    }


    public void GetPaths() throws IOException {
        final Path dir = fileSystem.getPath("");

        Path path = Files.createDirectory(dir);
        System.out.println(path.toAbsolutePath());
    }

    public void WriteFile() throws IOException {
        if (!CreateTemporaryFile())
            return;

        String data = "Some_Text\n\n";
        Path file = fileSystem.getPath(TEMP_FILE_NAME);

        String content = new String(Files.readAllBytes(file));
        System.out.println("File content before: " + content);

        Files.write(file, data.getBytes());

        // sleep(3);

        content = new String(Files.readAllBytes(file));
        System.out.println("File content before: " + content);
    }

    public void AppendToFile() throws IOException {
        if (!CreateTemporaryFile())
            return;

        ArrayList<String> lines = new ArrayList<String>(Arrays.asList("One", "Two", "Three", "Four", "Five"));
        Path file = fileSystem.getPath(TEMP_FILE_NAME);

        String content = "";
        for (String line: lines) {
            System.out.println("File content before: " + new String(Files.readAllBytes(file)));
            Files.write(file, (line + "\n").getBytes(), StandardOpenOption.APPEND);
            System.out.println("File content after: " + new String(Files.readAllBytes(file)));
            System.out.println("-------------------------------------------------------");
        }
    }
}

public class BaseTests {
    /** MemoryFileSystem tester: **/
    private static MemoryFileSystemTester tester = null;

    static {
        try {
            tester = new MemoryFileSystemTester();
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        // tester.GetPaths();

        // tester.WriteFile();

        tester.AppendToFile();
    }
}
