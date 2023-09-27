package utils;

import com.github.javaparser.ast.body.Parameter;

import static com.github.javaparser.StaticJavaParser.parseType;

public class ParameterFactory {
    public static Parameter getParam(Class<?> clazz,
                                     String name,
                                     boolean isVarArg) {
        var type = parseType(clazz.getSimpleName());
        return new Parameter(type, "args").setVarArgs(isVarArg);
    }
}
