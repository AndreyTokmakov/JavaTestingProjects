/****************************************************************************
* Copyright 2020 (C) Andrey Tokmakov
* TestNG tests enabling/disabling demo
*
* @name    : DisableTestDemo.java
* @author  : Tokmakov Andrey
* @version : 1.0
* @since   : November 18, 2020
****************************************************************************/ 

package Basics.Parralel_Tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

// !!!!!!!!!!!!!!!
// Tests should be started using ParallelMethodTest_Config.xml
//

public class ParallelMethodTest 
{
    @BeforeMethod
    public void beforeMethod() {
        long id = Thread.currentThread().getId();
        System.out.println("Before test-method. Thread id is: " + id);
    }
 
    @Test
    public void testMethodsOne() {
        long id = Thread.currentThread().getId();
        System.out.println("Simple test-method One. Thread id is: " + id);
    }
 
    @Test
    public void testMethodsTwo() {
        long id = Thread.currentThread().getId();
        System.out.println("Simple test-method Two. Thread id is: " + id);
    }
 
    @AfterMethod
    public void afterMethod() {
        long id = Thread.currentThread().getId();
        System.out.println("After test-method. Thread id is: " + id);
    }
}