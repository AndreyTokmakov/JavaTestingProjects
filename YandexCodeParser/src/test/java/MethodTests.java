import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import types.Annotation;
import utils.CompilationUnitFinder;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;


// TODO: Implement find methods by name
// TODO: Implement find methods by public/private/protected

// TODO: getMainMethod()  --->  It returns only first method

// TODO: getMethodsWithAnnotation()
// TODO: getMethodsWithAnnotation(Annotation)
// TODO: getMethodDeclarations()

// FIXME: Methods for single class -- Implement


// TODO: Use AssertJ ??
public class MethodTests extends TestBase {
    private final static Path testDir = TEST_DATA_FOLDER.resolve("methods");

    private List<MethodDeclaration> getAllMethods(CompilationUnit unit) {
        return CompilationUnitFinder.getAllMethods(unit);
    }

    @Test
    public void GetSingleMethodTest() throws IOException
    {
        final CompilationUnit unit = getCompilationUnitFromFile(testDir.resolve("SingleMethod.java"));
        final List<MethodDeclaration> methods = getAllMethods(unit);

        Assertions.assertEquals(1, methods.size());
        Assertions.assertEquals(methods.get(0).getNameAsString(), "sample");
    }

    @Test
    public void MultipleMethodsTest() throws IOException
    {
        final CompilationUnit unit = getCompilationUnitFromFile(testDir.resolve("MultipleMethods.java"));
        final List<MethodDeclaration> methods = getAllMethods(unit);

        Assertions.assertEquals(5, methods.size());

        Assertions.assertEquals(methods.get(0).getNameAsString(), "methodOnePublic");
        Assertions.assertEquals(methods.get(1).getNameAsString(), "methodTwoProtected");
        Assertions.assertEquals(methods.get(2).getNameAsString(), "methodThreePrivate");
        Assertions.assertEquals(methods.get(3).getNameAsString(), "methodFourPublicStatic");
        Assertions.assertEquals(methods.get(4).getNameAsString(), "methodFourStaticPublicAbstract");
    }

    @Test
    public void MultipleClassesMethods() throws IOException
    {
        final CompilationUnit unit =
                getCompilationUnitFromFile(testDir.resolve("MultipleClassWithSingleMethod.java"));
        final List<MethodDeclaration> methods = getAllMethods(unit);

        Assertions.assertEquals(3, methods.size());

        Assertions.assertEquals(methods.get(0).getNameAsString(), "sampleOne");
        Assertions.assertEquals(methods.get(1).getNameAsString(), "sampleTwo");
        Assertions.assertEquals(methods.get(2).getNameAsString(), "sampleThree");
    }

    @Test
    public void MethodsWithAnnotations() throws IOException
    {
        final CompilationUnit unit =
                getCompilationUnitFromFile(testDir.resolve("MethodsWithAnnotations.java"));

        final List<MethodDeclaration> deprecatedMethods =
                CompilationUnitFinder.getMethodsWithAnnotation(unit, "Deprecated");

        Assertions.assertEquals(2, deprecatedMethods.size());
        Assertions.assertEquals(deprecatedMethods.get(0).getNameAsString(), "deprecatedMethodOne");
        Assertions.assertEquals(deprecatedMethods.get(1).getNameAsString(), "deprecatedMethodTwo");

        final List<MethodDeclaration> testMethods =
                CompilationUnitFinder.getMethodsWithAnnotation(unit, "Test");

        Assertions.assertEquals(2, testMethods.size());
        Assertions.assertEquals(testMethods.get(0).getNameAsString(), "testMethodOne");
        Assertions.assertEquals(testMethods.get(1).getNameAsString(), "testMethodTwo");

        final List<MethodDeclaration> taggedMethods =
                CompilationUnitFinder.getMethodsWithAnnotation(unit, "Tags");

        Assertions.assertEquals(1, taggedMethods.size());
        Assertions.assertEquals(taggedMethods.get(0).getNameAsString(), "someMethodWithTags");
    }

    public enum TestAnnotations implements Annotation {
        Deprecated ("Deprecated"),
        Test ("Test");

        @Override
        public String getValue() {
            return value;
        }

        TestAnnotations(String value) {
            this.value = value;
        }

        final String value;
    }

    @Test
    public void MethodsWithAnnotationsEnum() throws IOException
    {
        final CompilationUnit unit =
                getCompilationUnitFromFile(testDir.resolve("MethodsWithAnnotations.java"));

        final List<MethodDeclaration> deprecatedMethods =
                CompilationUnitFinder.getMethodsWithAnnotation(unit, TestAnnotations.Deprecated);

        Assertions.assertEquals(2, deprecatedMethods.size());
        Assertions.assertEquals(deprecatedMethods.get(0).getNameAsString(), "deprecatedMethodOne");
        Assertions.assertEquals(deprecatedMethods.get(1).getNameAsString(), "deprecatedMethodTwo");

        final List<MethodDeclaration> testMethods =
                CompilationUnitFinder.getMethodsWithAnnotation(unit, TestAnnotations.Test);

        Assertions.assertEquals(2, testMethods.size());
        Assertions.assertEquals(testMethods.get(0).getNameAsString(), "testMethodOne");
        Assertions.assertEquals(testMethods.get(1).getNameAsString(), "testMethodTwo");
    }

    @Test
    public void GetMethodDeclarationsTest() throws IOException {
        final CompilationUnit unit =
                getCompilationUnitFromFile(testDir.resolve("MethodDeclarationTest.java"));

        Assertions.assertEquals(2,
                CompilationUnitFinder.getMethodDeclarations(unit, "publicMethodOne").size());
        Assertions.assertEquals(2,
                CompilationUnitFinder.getMethodDeclarations(unit, "protectedMethodOne").size());
        Assertions.assertEquals(2,
                CompilationUnitFinder.getMethodDeclarations(unit, "privateMethodOne").size());
        Assertions.assertEquals(2,
                CompilationUnitFinder.getMethodDeclarations(unit, "staticMethodOne").size());

        Assertions.assertEquals(1,
                CompilationUnitFinder.getMethodDeclarations(unit, "publicMethodTwo").size());
        Assertions.assertEquals(1,
                CompilationUnitFinder.getMethodDeclarations(unit, "protectedMethodTwo").size());
        Assertions.assertEquals(1,
                CompilationUnitFinder.getMethodDeclarations(unit, "privateMethodTwo").size());
        Assertions.assertEquals(1,
                CompilationUnitFinder.getMethodDeclarations(unit, "staticMethodTwo").size());
    }

    @Test
    @Disabled("Just for test")
    public void GetMainMethod() throws IOException
    {
        final CompilationUnit unit = getCompilationUnitFromFile(testDir.resolve("MainMethod.java"));
        final Optional<MethodDeclaration> mainMethod = CompilationUnitFinder.getMainMethod(unit);

        System.out.println("-".repeat(99));
        System.out.println(mainMethod);
        System.out.println("-".repeat(99));
    }
}