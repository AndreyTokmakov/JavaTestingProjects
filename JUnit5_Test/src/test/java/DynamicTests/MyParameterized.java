/****************************************************************************
* Copyright 2020 (C) Andrey Tokmakov
* MyParameterized pattern demo class
*
* @name    : ObeserverDemo.java
* @author  : Tokmakov Andrey
* @version : 1.0
* @since   : November 26, 2020
****************************************************************************/ 

package DynamicTests;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.params.ParameterizedTest;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@ParameterizedTest
@TestFactory
public @interface MyParameterized {

}