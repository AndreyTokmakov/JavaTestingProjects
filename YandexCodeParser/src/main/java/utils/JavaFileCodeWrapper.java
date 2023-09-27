package utils;

import com.github.javaparser.ParseProblemException;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.stmt.BlockStmt;
import filesystem.FileSystem;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class JavaFileCodeWrapper implements JavaCodeWrapper
{
    private final static String PACKAGE_NAME = "ru.yandex.praktikum.solution";
    private final static String PACKAGE_KEYWORD = "package";

    protected String javaCode;
    protected File wrappedFile;

    @Override
    public File getSolutionFile() {
        return wrappedFile;
    }

    /**
     * Wrapper object around student code
     *
     * @param javaCode - original student code
     */
    public JavaFileCodeWrapper(String javaCode) {
        this.javaCode = javaCode.strip();
    }

    /**
     * Wrapper object around student code
     *
     * @param studentCodePath - path to file with student code
     */
    public JavaFileCodeWrapper(Path studentCodePath) {
        if (!studentCodePath.toFile().exists()) {
            System.err.printf("The specified file with the solution code %s was not found", studentCodePath);
            System.exit(1);
        }

        final Path absolutePath = studentCodePath.toAbsolutePath();
        if (!absolutePath.toFile().isFile()) {
            // log.error("{} не является файлом", absolutePath.toString());
            System.err.printf("%s is not a file", absolutePath.toString());
            System.exit(1);
        }

        try {
            this.javaCode = FileSystem.readTextFromFile(absolutePath).strip();
            // System.out.println("-".repeat(99) + '\n' + this.javaCode + '\n' +  "-".repeat(99) );
        } catch (IOException e) {
            // log.error("Возникли ошибки при чтении кода решения");
            // log.debug(e);
            System.err.println("Errors occurred when reading the solution code");
            System.exit(1);
        }
    }

    public <T extends JavaFileCodeWrapper> T castTo(Class<T> clazz) {
        return clazz.cast(this);
    }

    public JavaFileCodeWrapper wrapPackage(String packageName) {
        if (javaCode.contains(PACKAGE_KEYWORD)) {
            final String packageImport = String.format("package %s;%n", packageName);
            // Replace first line of the code from file with value of 'packageImport'
            javaCode = javaCode.replaceFirst("package (.*);", packageImport);
        } else {
            javaCode = String.format("package %s;%n%n%s", packageName, javaCode);
        }
        return this;
    }

    public JavaFileCodeWrapper wrapMainPackage() {
        return wrapPackage(PACKAGE_NAME);
    }

    public JavaFileCodeWrapper wrapClass(String className) {
        // if student already has a class - leave it untouched
        if (!getTopClassName().isBlank())
            return this;

        className = className.replace(".java", "");
        javaCode = String.format("public class %s {%n%n %s %n}%n", className, javaCode);
        return this;
    }

    /**
     * Wrap class around student code and put it into random package.
     */
    public JavaFileCodeWrapper wrapMainClass() {
        String topClassName = getTopClassName();

        if (topClassName.isBlank())
            topClassName = "Program";

        return wrapClass(topClassName);
    }

    public JavaFileCodeWrapper wrapMainMethod() {
        // TODO: write validation if method already exist
        javaCode = String.format("public static void main(String[] args) {%n%s%n}", javaCode);
        return this;
    }

    public JavaFileCodeWrapper wrap() {
        wrapMainMethod();
        wrapMainClass();
        wrapMainPackage();
        return this;
    }

    public JavaFileCodeWrapper addClass(String rawClassDeclaration) {
        try {
            var clazz = StaticJavaParser.parseClassOrInterfaceType(rawClassDeclaration);
            var className = clazz.getNameAsString();
            var unit = StaticJavaParser.parse(javaCode);
            var isClassExist = CompilationUnitFinder.getTopTypes(unit).stream().anyMatch(it -> it.getNameAsString().equals(className));
            if (isClassExist) {
                System.err.println(String.format("Class $s already exist", className));
                return this;
            }
        } catch (Exception e) {
            // do nothing
        }

        try {
            javaCode += System.lineSeparator() + System.lineSeparator() + rawClassDeclaration;
        } catch (final Exception exc) {
            reportExceptionAbort(exc);
        }

        return this;
    }

    /**
     * @return Package + Class Name. Eg: ru.yandex.xxx.ddd.Solution
     */
    public String getFullClassName() {
        var packageName = getPackageName();
        return packageName.isBlank() ? getTopClassName() : packageName + "." + getTopClassName();
    }

    public String getPackageName() {
        var packageName = "";

        var pattern = Pattern.compile("package (.*);");
        var matcher = pattern.matcher(javaCode);

        while (matcher.find()) {
            var packageLine = matcher.group().strip();
            return packageLine
                    .replace("package", "")
                    .replace(";", "")
                    .strip();
        }

        return packageName;
    }

    public JavaFileCodeWrapper addTopLevelClassInstanceVariable() {
        try {
            var unit = StaticJavaParser.parse(javaCode);
            var topClass = CompilationUnitFinder.getFirstTopType(unit).get();
            var className = CompilationUnitFinder.getFirstTopType(unit).get().getName().asString();

            topClass.addFieldWithInitializer(topClass.getNameAsString(),
                    Data.generateVariableName(),
                    StaticJavaParser.parseExpression(String.format("new %s()", className)),
                    Modifier.Keyword.PUBLIC);

            javaCode = unit.toString();
        } catch (final Exception exc) {
            reportExceptionAbort(exc);
        }

        return this;
    }

    public JavaFileCodeWrapper addMethod(String name,
                                         String methodBody,
                                         String returnType,
                                         Parameter param,
                                         Modifier.Keyword... modifiers) {
        return addMethod(name, methodBody, returnType, Collections.singletonList(param), modifiers);
    }

    /**
     * Add main method and insert plain text code into method body.
     *
     * @param methodBody - should end with semicolon
     * @return
     */
    public JavaFileCodeWrapper addMethod(String name, String methodBody, String returnType, List<Parameter> params, Modifier.Keyword... modifiers) {
        try {
            if (name.strip().isBlank())
                throw new IllegalArgumentException("Название метода не может быть пустым");

            var unit = StaticJavaParser.parse(javaCode);
            var topLevelTypeOptional = CompilationUnitFinder.getFirstTopType(unit);
            if (topLevelTypeOptional.isEmpty()) System.exit(1);
            var topLevelType = topLevelTypeOptional.get();

            var method = topLevelType.addMethod(name, modifiers);
            method.setType(returnType);

            if (params != null)
                params.forEach(method::addParameter);

            if (!(methodBody == null || methodBody.isBlank())) {
                var statement = new BlockStmt();
                Arrays.stream(methodBody.split(System.lineSeparator()))
                        .forEach(statement::addStatement);

                method.setBody(statement);
            }

            javaCode = unit.toString();
        } catch (final Exception exc) {
            reportExceptionAbort(exc);
        }

        return this;
    }

    public JavaFileCodeWrapper addMainMethod() {
        return addMainMethod("");
    }

    public JavaFileCodeWrapper addMainMethod(String mainMethodBody) {
        try {
            var unit = StaticJavaParser.parse(javaCode);
            var topLevelTypeOptional = CompilationUnitFinder.getFirstTopType(unit);
            if (topLevelTypeOptional.isEmpty()) System.exit(1);
            var topLevelType = topLevelTypeOptional.get();

            var mainMethods = topLevelType.getMethods().stream()
                    .filter(it -> it.getName().asString().equals("main"))
                    .collect(Collectors.toList());

            if (!mainMethods.isEmpty()) return this;
        } catch (final Exception exc) {
            reportExceptionAbort(exc);
        }

        var params = ParameterFactory.getParam(String.class, "args", true);

        return addMethod("main",
                mainMethodBody,
                "void",
                params,
                Modifier.Keyword.PUBLIC,
                Modifier.Keyword.STATIC);
    }

    public JavaFileCodeWrapper addParameterlessMethodCallFromMain(String methodName) {
        try {
            var unit = StaticJavaParser.parse(javaCode);

            var mainMethods = CompilationUnitFinder.getMethodDeclarations(unit, "main");
            var main = mainMethods.get(0);
            var mainMethodBody = new BlockStmt();

            var className = CompilationUnitFinder.getFirstTopType(unit).get().getName().asString();
            var classInstanceVar = new VariableDeclarator(StaticJavaParser.parseClassOrInterfaceType(className), Data.generateVariableName());
            classInstanceVar.setInitializer(String.format("new %s()", className));

            var classInstanceDeclaration = new VariableDeclarationExpr(classInstanceVar);
            var methodCall = new MethodCallExpr(new NameExpr(classInstanceVar.getName()), methodName);
            mainMethodBody.addStatement(classInstanceDeclaration);
            mainMethodBody.addStatement(methodCall);
            main.setBody(mainMethodBody);

            javaCode = unit.toString();
        } catch (final Exception exc) {
            reportExceptionAbort(exc);
        }
        return this;
    }

    public JavaFileCodeWrapper setMethodCallFromMain(String methodName, String argumentValue, boolean printReturnToOutput) {
        return setMethodCallFromMain(methodName, StaticJavaParser.parseExpression(argumentValue), printReturnToOutput);
    }

    public JavaFileCodeWrapper setMethodCallFromMain(String methodName, Expression argumentValue, boolean printReturnToOutput) {
        try {
            var unit = StaticJavaParser.parse(javaCode);

            var mainMethods = CompilationUnitFinder.getMethodDeclarations(unit, "main");
            var main = mainMethods.get(0);
            var mainMethodBody = new BlockStmt();

            var className = CompilationUnitFinder.getFirstTopType(unit).get().getName().asString();
            var classInstanceVar = new VariableDeclarator(StaticJavaParser.parseClassOrInterfaceType(className), Data.generateVariableName());
            classInstanceVar.setInitializer(String.format("new %s()", className));

            mainMethodBody.addStatement(new VariableDeclarationExpr(classInstanceVar));

            var methodCall = new MethodCallExpr(new NameExpr(classInstanceVar.getName()), methodName);
            methodCall.addArgument(argumentValue);

            if (printReturnToOutput) {
                var sysOutputMethodCall = new MethodCallExpr(new NameExpr("System.out"), "println");
                sysOutputMethodCall.addArgument(methodCall);
                mainMethodBody.addStatement(sysOutputMethodCall);
            } else {
                mainMethodBody.addStatement(methodCall);
            }

            main.setBody(mainMethodBody);
            javaCode = unit.toString();
        } catch (final Exception exc) {
            reportExceptionAbort(exc);
        }
        return this;
    }

    public JavaFileCodeWrapper setMethodCallFromMain(String methodName, String argumentValue) {
        var expression = new StringLiteralExpr(argumentValue);
        return setMethodCallFromMain(methodName, expression, false);
    }

    /**
     * Parse passed expression and insert it into main method body as a expression.
     *
     * @param plainTextMethodCall Eg: "methodName(10, \"some text argument\");"
     * @return
     */
    public JavaFileCodeWrapper setMethodCallFromMain(String plainTextMethodCall) {
        try {
            var unit = StaticJavaParser.parse(javaCode);

            var mainMethods = CompilationUnitFinder.getMethodDeclarations(unit, "main");
            var main = mainMethods.get(0);
            var mainMethodBody = new BlockStmt();

            var className = CompilationUnitFinder.getFirstTopType(unit).get().getName().asString();
            var classInstanceVar = new VariableDeclarator(StaticJavaParser.parseClassOrInterfaceType(className),
                    Data.generateVariableName());
            classInstanceVar.setInitializer(String.format("new %s()", className));

            mainMethodBody.addStatement(new VariableDeclarationExpr(classInstanceVar));

            var hardcoreMethodCall = String.format("%s.%s", classInstanceVar.getName(), plainTextMethodCall);
            mainMethodBody.addStatement(hardcoreMethodCall);

            main.setBody(mainMethodBody);

            javaCode = unit.toString();
        } catch (final Exception exc) {
            reportExceptionAbort(exc);
        }
        return this;
    }

    public JavaFileCodeWrapper addRawMethodDeclaration(String rawMethodDeclaration) {
        try {
            var unit = StaticJavaParser.parse(javaCode);
            var topClass = CompilationUnitFinder.getFirstTopType(unit).get();

            var method = StaticJavaParser.parseMethodDeclaration(rawMethodDeclaration);
            topClass.addMember(method);

            javaCode = unit.toString();
        } catch (final ParseProblemException exc) { // TODO: Write wrapper for duplicate code
            // new ExceptionLogger(log, javaCode).logSyntaxException(exc);
            System.err.println(exc.toString());
            System.err.println(javaCode);
            System.exit(1);
        } catch (final Exception exc) {
            reportExceptionAbort(exc);
        }
        return this;
    }

    public List<String> getRawMethodCalls(String methodName) {
        var methodCalls = new ArrayList<String>();

        // method****;
        var pattern = Pattern.compile(String.format("%s.*;", methodName));
        var matcher = pattern.matcher(javaCode);

        while (matcher.find()) {
            var methodCallText = matcher.group().strip();
            methodCalls.add(methodCallText);
        }

        return methodCalls;
    }

    public JavaFileCodeWrapper replaceRawMethodCalls(List<String> methodCalls, String replacement) {
        for (var call : methodCalls) {
            javaCode = javaCode.replace(call, replacement);
        }

        return this;
    }


    /**
     * Find top level class name declaration
     *
     * @return Class name
     */
    public String getTopClassName() {
        try {
            var compilationUnit = StaticJavaParser.parse(javaCode);
            var topLevelType = CompilationUnitFinder.getFirstTopType(compilationUnit);
            return topLevelType.isEmpty() ? "" : topLevelType.get().getNameAsString();
        } catch (ParseProblemException e) {
            // Eg: "class XXXXX {"
            var pattern = Pattern.compile("class (.*)\\{");
            var matcher = pattern.matcher(javaCode);
            if (!matcher.find())
                return "";

            var className = matcher.group().strip();
            if (className.isBlank())
                return "";
            else return className.replace("class", "")
                    .replace("{", "").strip();
        }
    }

    @Override
    public Path store() {
        try {
            final String topClassName = getTopClassName();
            final String tmpDir = System.getProperty("java.io.tmpdir");
            final Path filePath = Path.of(tmpDir,
                    getPackageName().replace(".", File.separator),
                    topClassName + ".java");

            // Java restriction - filename should match class name
            wrappedFile = filePath.toFile();
            final File parentDir = wrappedFile.getParentFile();
            if (!parentDir.mkdirs() && !parentDir.isDirectory()) {
                System.err.println("Failed to create folder '" + parentDir + "'");
                System.exit(1);
            }

            FileSystem.writeTextToFile(javaCode, wrappedFile);
        } catch (final Exception exc) {
            System.err.println("There were problems when modifying the solution code");
            System.err.println(exc.toString());
            System.err.println(javaCode);
        }

        return wrappedFile.toPath();
    }


    @Override
    public String toString() {
        return javaCode;
    }

    private void reportExceptionAbort(final Exception exc) {
        // new ExceptionLogger(log, javaCode).logSyntaxException(e
        System.err.println(exc.toString());
        System.err.println(javaCode);
        System.exit(1);
    }
}
