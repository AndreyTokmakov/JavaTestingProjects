import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import utils.CompilationUnitFinder;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

// TODO: Use AssertJ ??
public class TopLevelTypesTests extends TestBase {
    private final static Path testDir = TEST_DATA_FOLDER.resolve("top_level_types");

    private List<ClassOrInterfaceDeclaration> getTopTypes(String className) throws IOException {
        final CompilationUnit unit = getCompilationUnitFromFile(testDir.resolve(className));
        return CompilationUnitFinder.getTopTypes(unit);
    }

    @Test
    public void noTopLevelTypes() throws IOException
    {
        final List<ClassOrInterfaceDeclaration> topTypes = getTopTypes("EmptyFile.java");

        Assertions.assertEquals(0, topTypes.size());
    }

    @Test
    public void SingleTypeTest() throws IOException
    {
        final List<ClassOrInterfaceDeclaration> topTypes = getTopTypes("SingleType.java");

        Assertions.assertEquals(1, topTypes.size());
        Assertions.assertEquals(topTypes.get(0).getNameAsString(), "OneSingleTopClass");
    }

    @Test
    public void MultipleTypeTest() throws IOException
    {
        final List<ClassOrInterfaceDeclaration> topTypes = getTopTypes("MultipleTypes.java");

        Assertions.assertEquals(5, topTypes.size());

        Assertions.assertEquals(topTypes.get(0).getNameAsString(), "TopLevelClassOne");
        Assertions.assertEquals(topTypes.get(1).getNameAsString(), "TopLevelClassTwo");
        Assertions.assertEquals(topTypes.get(2).getNameAsString(), "TopLevelClassThree");
        Assertions.assertEquals(topTypes.get(3).getNameAsString(), "SomeInterfaceOne");
        Assertions.assertEquals(topTypes.get(4).getNameAsString(), "SomeInterfaceTwo");
    }

    @Test
    public void NestedClassesShallNotReturned() throws IOException
    {
        final List<ClassOrInterfaceDeclaration> topTypes = getTopTypes("WithNestedClasses.java");

        Assertions.assertEquals(2, topTypes.size());
        Assertions.assertEquals(topTypes.get(0).getNameAsString(), "TopLevelClassOne");
        Assertions.assertEquals(topTypes.get(1).getNameAsString(), "TopLevelClassTwo");
    }

    @Test
    @Disabled("This fails... fix it")
    public void EnumTypesTest() throws IOException
    {
        final List<ClassOrInterfaceDeclaration> topTypes = getTopTypes("EnumTypes.java");

        Assertions.assertEquals(2, topTypes.size());
        Assertions.assertEquals(topTypes.get(0).getNameAsString(), "Months");
        Assertions.assertEquals(topTypes.get(1).getNameAsString(), "Cities");
    }
}
