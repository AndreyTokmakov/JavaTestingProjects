import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class TestBase {
    protected final static Path TEST_DATA_FOLDER = Paths.get("src/test/resources");

    protected CompilationUnit getCompilationUnitFromFile(Path filePath) throws IOException {
        return StaticJavaParser.parse(filePath);
    }
}
