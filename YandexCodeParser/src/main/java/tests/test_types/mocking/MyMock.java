package tests.test_types.mocking;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public //can use in method only.
@interface MyMock {
    // should ignore this test?
    public boolean enabled() default true;
}