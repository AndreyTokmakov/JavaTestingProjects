package tests;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.ObjectCreationExpr;
import com.github.javaparser.ast.stmt.BlockStmt;
import process.CommandExecutor;
import process.ProcessDecorator;
import utils.CompilationUnitFinder;
import utils.JavaFileCodeWrapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Tests
{
    protected final static Path TEST_DATA_FOLDER = Paths.get("YandexCodeParser/src/main/java/tests/data/");

    void ExecuteCodeTest() throws IOException {
        Path path = TEST_DATA_FOLDER.resolve("TestClass1.java");

        Path wrappedCodePath = new JavaFileCodeWrapper(path).wrapMainPackage().store();
        System.out.println("Temporary file created: " + wrappedCodePath);

        ProcessDecorator result = CommandExecutor.runJavaCode(wrappedCodePath);
        Process process = result.getProcess();

        BufferedReader error = new BufferedReader(new InputStreamReader(result.getProcess().getInputStream()));
        System.out.println(error.readLine());
    }

    void GetMethodsWithAnnotation() throws IOException
    {
        final Path filePath = Path.of(TEST_DATA_FOLDER + "MethodsDeclaration.java");

        final CompilationUnit unit = StaticJavaParser.parse(filePath);

        final List<MethodDeclaration> testMethods =
                CompilationUnitFinder.getMethodsWithAnnotation(unit, "MyTest");

        for (MethodDeclaration mdecl: testMethods)
            System.out.println("-".repeat(99) + '\n' + mdecl);
        System.out.println("-".repeat(99));
    }

    void GetMethodsByName() throws IOException
    {
        final Path filePath = TEST_DATA_FOLDER.resolve("MethodsDeclaration.java");
        final CompilationUnit unit = StaticJavaParser.parse(filePath);
        final List<MethodDeclaration> methods = CompilationUnitFinder.getMethodDeclarations(unit, "test2");

        System.out.println(methods);
    }

    void GetClassMethods() throws IOException, ClassNotFoundException {
        final Path filePath = TEST_DATA_FOLDER.resolve("MethodsDeclaration.java");
        final CompilationUnit unit = StaticJavaParser.parse(filePath);
        final ClassOrInterfaceDeclaration testClass =
                CompilationUnitFinder.getTopTypeByName(unit, "MethodsDeclarationTwo");


        // final List<MethodDeclaration> methods = CompilationUnitFinder.getMethodDeclarations(unit, "test2");
        System.out.println(testClass.getMethods());
    }

    void GetMethodsCalls() throws IOException
    {
        final Path filePath = Path.of(TEST_DATA_FOLDER + "MethodsCalls.java");
        final CompilationUnit unit = StaticJavaParser.parse(filePath);
        final BlockStmt methodBody = CompilationUnitFinder.getMethodDeclarations(unit, "func1")
                .stream().findFirst().get().getBody().get();

        // System.out.println(methodBody);

        final List<MethodCallExpr> method = methodBody.findAll(MethodCallExpr.class);

        for (MethodCallExpr methodCall: method)
        {
            System.out.println("-".repeat(99));
            System.out.println(methodCall);
            System.out.println("   scope: " + methodCall.getScope());
            System.out.println("   name : " + methodCall.getNameAsString());
            if (methodCall.getArguments().isNonEmpty())
                System.out.println("   args : " + methodCall.getArguments());
        }
    }

    void GetMethodsCalls_ByScope() throws IOException
    {
        final Path filePath = Path.of(TEST_DATA_FOLDER + "MethodsCalls.java");
        final CompilationUnit unit = StaticJavaParser.parse(filePath);
        final BlockStmt methodBody = CompilationUnitFinder.getMethodDeclarations(unit, "func1")
                .stream().findFirst().get().getBody().get();

        final List<MethodCallExpr> method =
                CompilationUnitFinder.getMethodCallsByScope(methodBody, "this");
        for (MethodCallExpr methodCall: method)
        {
            System.out.println("-".repeat(99));
            System.out.println(methodCall);
            System.out.println(methodCall.getNameAsString());
        }
    }

    void TestVariableDeclarationALL() throws IOException
    {
        final Path filePath = Path.of(TEST_DATA_FOLDER + "VariableDeclarations.java");
        final CompilationUnit unit = StaticJavaParser.parse(filePath);

        final List<MethodDeclaration> testMethods =
                CompilationUnitFinder.getMethodsWithAnnotation(unit, "MyTest");
        final MethodDeclaration method = testMethods.get(0);

        List<VariableDeclarator> allVariables =
                CompilationUnitFinder.getVariablesDeclaration(method.getBody().get());
        for (VariableDeclarator varDecl: allVariables) {
            System.out.println(varDecl);
            System.out.println("   Type: " + varDecl.getTypeAsString());
            System.out.println("   Name: " + varDecl.getNameAsString());
            System.out.println("   Initializer: " + varDecl.getInitializer());
            if (varDecl.getInitializer().isPresent()) {
                Expression init = varDecl.getInitializer().get();
                if (init.isObjectCreationExpr())
                {
                    ObjectCreationExpr objInitExpr = varDecl.getInitializer().get().asObjectCreationExpr();
                    System.out.println("        Expr: " + objInitExpr);
                    System.out.println("        Type: " + objInitExpr.getType());
                    System.out.println("        Args: " + objInitExpr.getArguments());
                }
            }

            System.out.println("   Position : " + varDecl.getBegin());
            System.out.println();
        }
    }

    void TestVariableDeclaration() throws IOException
    {
        final Path filePath = Path.of(TEST_DATA_FOLDER + "VariableDeclarations.java");
        final CompilationUnit unit = StaticJavaParser.parse(filePath);

        final List<MethodDeclaration> testMethods =
                CompilationUnitFinder.getMethodsWithAnnotation(unit, "MyTest");
        final MethodDeclaration method = testMethods.get(0);

        List<VariableDeclarator> variables =
                CompilationUnitFinder.getVariablesDeclaration(method.getBody().get(), "Cookie");

        for (VariableDeclarator varDecl: variables) {
            System.out.println("-".repeat(99));
            System.out.println(varDecl);

            String varName  = varDecl.getNameAsString();
            System.out.println("Name: " + varName);
        }
    }

    void GetAssignmentExpressions() throws IOException {
        final Path filePath = Path.of(TEST_DATA_FOLDER + "VariableDeclarations.java");
        final CompilationUnit unit = StaticJavaParser.parse(filePath);

        final List<MethodDeclaration> testMethods =
                CompilationUnitFinder.getMethodsWithAnnotation(unit, "MyTest");
        final MethodDeclaration method = testMethods.get(0);

        List<AssignExpr> assignments =
                CompilationUnitFinder.getAssignmentExpressions(method.getBody().get());


        System.out.println(assignments);
    }

    public static List<MethodCallExpr> getMethodCallsByKeywords(BlockStmt bodyStatement,
                                                                List<String> keywords) {
        return bodyStatement.findAll(MethodCallExpr.class)
                .stream().filter(it -> {
                    final String exprStr = it.toString();
                    return  keywords.stream().allMatch(exprStr::contains);
                }).collect(Collectors.toList());
    }

    void ParseMockingCall() throws IOException {
        final Path filePath = TEST_DATA_FOLDER.resolve("MockingTest.java");
        final CompilationUnit unit = StaticJavaParser.parse(filePath);

        final List<MethodDeclaration> testMethods =
                CompilationUnitFinder.getMethodsWithAnnotation(unit, "MyTest");
        final MethodDeclaration method = testMethods.get(0);

        BlockStmt testBody = testMethods.get(0).getBody().get();
        List<MethodCallExpr> exprList = getMethodCallsByKeywords(testBody,
                List.of("MockitoEx", "when", "thenReturn(500010)"));

        System.out.println("=".repeat(120));
        System.out.println(exprList);
        System.out.println("=".repeat(120));
    }



    public static void main(String[] args) throws IOException, ClassNotFoundException {
        final Tests test = new Tests();

        // test.ExecuteCodeTest();

        // test.GetMethodsWithAnnotation();
        // test.GetMethodsByName();
        // test.GetClassMethods();

        // test.GetMethodsCalls();
        // test.GetMethodsCalls_ByScope();

        // test.TestVariableDeclarationALL();
        // test.TestVariableDeclaration();

        // test.GetAssignmentExpressions();

        test.ParseMockingCall();

    }
}
