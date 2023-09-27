/****************************************************************************
* Copyright 2021 (C) Andrey Tokmakov
* PerformanceApiTag.java class
*
* @name    : PerformanceApiTag.java
* @author  : Tokmakov Andrey
* @version : 1.0
* @since   : Jan 9, 2021
****************************************************************************/

package Tags.custom_tags;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.RetentionPolicy; 
import java.lang.annotation.ElementType;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Tags(value = { @Tag("api"), @Tag("performance")})
@Test
public @interface PerformanceApiTag {

}