package utils;

import java.io.File;
import java.nio.file.Path;

public interface JavaCodeWrapper {
    public Path store();

    /**
     * @return File pointing on a single file or on a directory
     */
    public File getSolutionFile();
}
