package utils;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.stmt.*;
import com.github.javaparser.ast.type.Type;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import types.Annotation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CompilationUnitFinder {
    private static final Logger log = LogManager.getLogger(CompilationUnitFinder.class.getName());

    protected static String cleanup(String original) {
        return original.replace(" ", "");
    }

    public static List<ClassOrInterfaceDeclaration> getTypes(CompilationUnit unit) {
        return unit.getTypes().stream().filter(TypeDeclaration::isClassOrInterfaceDeclaration)
                .map(BodyDeclaration::asClassOrInterfaceDeclaration)
                .collect(Collectors.toList());
    }

    public static List<ClassOrInterfaceDeclaration> getTopTypes(CompilationUnit unit) {
        return unit.getTypes().stream().filter(it -> it.isTopLevelType() && it.isClassOrInterfaceDeclaration())
                .map(BodyDeclaration::asClassOrInterfaceDeclaration)
                .collect(Collectors.toList());
    }

    public static ClassOrInterfaceDeclaration getTopTypeByName(CompilationUnit unit,
                                                               String className)  throws ClassNotFoundException {
        var topType = getTypes(unit).stream().filter(
                it -> it.getNameAsString().equals(className)).findFirst();
        if (topType.isEmpty())
            throw new ClassNotFoundException("Не найден ожидаемый тип " + className);
        return topType.get().asClassOrInterfaceDeclaration();
    }

    public static Optional<ClassOrInterfaceDeclaration> getFirstTopType(CompilationUnit unit) {
        final List<ClassOrInterfaceDeclaration> topTypes = new ArrayList<>(getTopTypes(unit));
        final List<ClassOrInterfaceDeclaration> publicClasses = topTypes.stream().filter(
                it -> it.hasModifier(Modifier.Keyword.PUBLIC)).collect(Collectors.toList());
        return publicClasses.isEmpty() ? topTypes.stream().findFirst() :
                publicClasses.stream().findFirst();
    }

    public static Optional<FieldDeclaration> getTopLevelClassInstanceVariable(CompilationUnit unit) {
        final ClassOrInterfaceDeclaration topLevelClass = getFirstTopType(unit).get();
        final Stream<FieldDeclaration> classTypeFields = topLevelClass.getFields().stream()
                .filter(it -> it.getElementType().isClassOrInterfaceType()
                        && it.getElementType().asClassOrInterfaceType().getName().equals(topLevelClass.getName()));

        return classTypeFields.findFirst();
    }

    public static List<MethodDeclaration> getAllMethods(CompilationUnit unit) {
        return unit.findAll(MethodDeclaration.class);
    }

    public static Optional<MethodDeclaration> getMainMethod(CompilationUnit unit) {
        return unit.findAll(MethodDeclaration.class)
                .stream()
                .filter(it -> it.getNameAsString().equals("main"))
                .findFirst();
    }

    public static List<MethodDeclaration> getMethodsWithAnnotation(CompilationUnit unit,
                                                                   String annotationName) {
        return unit.findAll(MethodDeclaration.class)
                .stream()
                .filter(method -> {
                    final Optional<AnnotationExpr> annotation = method.getAnnotationByName(annotationName);
                    return annotation.isPresent() && annotation.get().getNameAsString().equals(annotationName);
                })
                .collect(Collectors.toList());
    }

    public static List<MethodDeclaration> getMethodsWithAnnotation(CompilationUnit unit,
                                                                   Annotation annotation) {
        return getMethodsWithAnnotation(unit, annotation.getValue());
    }

    public static List<ConstructorDeclaration> getConstructorDeclarations(CompilationUnit unit) {
        return new ArrayList<>(unit.findAll(ConstructorDeclaration.class));
    }

    public static List<MethodDeclaration> getMethodDeclarations(CompilationUnit unit,
                                                                String methodName) {
        return unit.findAll(MethodDeclaration.class)
                .stream()
                .filter(it -> it.getNameAsString().equals(methodName))
                .collect(Collectors.toList());
    }

    public static List<VariableDeclarator> getStringVariablesDeclaration(BlockStmt bodyStatement) {
        return bodyStatement.findAll(VariableDeclarationExpr.class).stream()
                .flatMap(it -> it.getVariables().stream())
                .filter(it -> {
                    final Type type = it.getType();
                    return type.isClassOrInterfaceType() && type.asClassOrInterfaceType().getNameAsString().equals("String");
                }).collect(Collectors.toList());
    }

    public static List<VariableDeclarator> getVariablesDeclaration(BlockStmt bodyStatement,
                                                                   Predicate<Type> typeFilter) {
        return bodyStatement
                .findAll(VariableDeclarationExpr.class)
                .stream().flatMap(it -> it.getVariables().stream())
                .filter(it -> typeFilter.test(it.getType()))
                .collect(Collectors.toList());
    }

    public static List<VariableDeclarator> getVariablesDeclaration(BlockStmt bodyStatement) {
        return bodyStatement
                .findAll(VariableDeclarationExpr.class)
                .stream().flatMap(it -> it.getVariables().stream())
                .collect(Collectors.toList());
    }

    public static List<VariableDeclarator> getVariablesDeclaration(BlockStmt bodyStatement,
                                                                   String typeDeclaration) {
        return getVariablesDeclaration(bodyStatement).stream().filter(it ->
                        cleanup(it.getTypeAsString()).equals(cleanup(typeDeclaration)))
                .collect(Collectors.toList());
    }

    public static List<VariableDeclarator> getArrayVariablesDeclaration(BlockStmt bodyStatement,
                                                                        String arrayTypeDeclaration) {
        return bodyStatement
                .findAll(VariableDeclarationExpr.class)
                .stream().flatMap(it -> it.getVariables().stream())
                .filter(it -> it.getType().isArrayType() && it.getType().toString().equals(arrayTypeDeclaration))
                .collect(Collectors.toList());
    }

    public static List<AssignExpr> getAssignmentExpressions(Statement bodyStatement) {
        return !bodyStatement.isBlockStmt() ? Collections.emptyList() :
                getAssignmentExpressions(bodyStatement.asBlockStmt());
    }

    public static List<AssignExpr> getAssignmentExpressions(BlockStmt bodyStatement) {
        return bodyStatement
                .getStatements().stream()
                .filter(Statement::isExpressionStmt)
                .map(it -> it.asExpressionStmt().getExpression())
                .filter(Expression::isAssignExpr)
                .map(Expression::asAssignExpr)
                .collect(Collectors.toList());
    }

    public static List<UnaryExpr> getUnaryExpressions(Statement bodyStatement) {
        return !bodyStatement.isBlockStmt() ? Collections.emptyList() :
                getUnaryExpressions(bodyStatement.asBlockStmt());
    }

    public static List<UnaryExpr> getUnaryExpressions(BlockStmt bodyStatement) {
        return bodyStatement
                .getStatements().stream()
                .filter(Statement::isExpressionStmt)
                .map(it -> it.asExpressionStmt().getExpression())
                .filter(Expression::isUnaryExpr)
                .map(Expression::asUnaryExpr)
                .collect(Collectors.toList());
    }

    public static List<MethodCallExpr> getMethodCalls(Statement bodyStatement,
                                                      String methodName) {
        return !bodyStatement.isBlockStmt() ? Collections.emptyList() :
                getMethodCalls(bodyStatement.asBlockStmt(), methodName);
    }

    public static List<MethodCallExpr> getMethodCalls(BlockStmt bodyStatement,
                                                      String methodName) {
        return bodyStatement.findAll(MethodCallExpr.class)
                .stream().filter(methodCall -> {
                    // Eg: System.out
                    final Optional<Expression> scope = methodCall.getScope();
                    final String scopeName = scope.isPresent() ? scope.get().toString() : "";
                    return (scopeName + "." + methodCall.getNameAsString()).endsWith(methodName);
                })
                .collect(Collectors.toList());
    }

    public static List<MethodCallExpr> getMethodCalls(BlockStmt bodyStatement,
                                                      String methodScope,
                                                      String methodName) {
        return bodyStatement.findAll(MethodCallExpr.class)
                .stream().filter(methodCall -> {
                    // Eg: System.out
                    final Optional<Expression> scope = methodCall.getScope();
                    final String scopeName = scope.isPresent() ? scope.get().toString() : "";
                    return (scopeName + "." + methodCall.getNameAsString()).equals(methodScope + "." + methodName);
                })
                .collect(Collectors.toList());
    }

    public static List<MethodCallExpr> getMethodCallsByScope(BlockStmt bodyStatement,
                                                             String scope) {
        return bodyStatement.findAll(MethodCallExpr.class).stream()
                .filter(it -> it.getScope().isPresent() && it.getScope().get().toString().startsWith(scope))
                .collect(Collectors.toList());
    }

    public static List<ForStmt> getForLoopStatements(Statement bodyStatement) {
        return !bodyStatement.isBlockStmt() ? Collections.emptyList() :
                getForLoopStatements(bodyStatement.asBlockStmt());
    }

    public static List<ForStmt> getForLoopStatements(BlockStmt bodyStatement) {
        return new ArrayList<>(bodyStatement.findAll(ForStmt.class));
    }

    public static List<ForEachStmt> getForEachStatements(BlockStmt bodyStatement) {
        return new ArrayList<>(bodyStatement.findAll(ForEachStmt.class));
    }

    public static List<WhileStmt> getWhileLoopStatements(BlockStmt bodyStatement) {
        return new ArrayList<>(bodyStatement.findAll(WhileStmt.class));
    }

    public static List<DoStmt> getDoWhileLoopStatements(BlockStmt bodyStatement) {
        return new ArrayList<>(bodyStatement.findAll(DoStmt.class));
    }

    public static List<TryStmt> getTryStatements(BlockStmt bodyStatement) {
        return new ArrayList<>(bodyStatement.findAll(TryStmt.class));
    }

    public static List<IfStmt> getIfStatements(BlockStmt bodyStatement) {
        return new ArrayList<>(bodyStatement.findAll(IfStmt.class));
    }

    public static List<ExplicitConstructorInvocationStmt> getSuperConstructorCalls(BlockStmt bodyStatement) {
        return new ArrayList<>(bodyStatement.findAll(ExplicitConstructorInvocationStmt.class));
    }
}
