/****************************************************************************
* Copyright 2020 (C) Andrey Tokmakov
* TestNG tests enabling/disabling demo
*
* @name    : ParallelClassesTestTwo.java
* @author  : Tokmakov Andrey
* @version : 1.0
* @since   : November 18, 2020
****************************************************************************/ 

package Basics.Parralel_Tests;

import org.testng.annotations.*;

public class ParallelClassesTestTwo 
{
    @BeforeClass
    public void beforeClass() {
        long id = Thread.currentThread().getId();
        System.out.println("Before test-class. Thread id is: " + id);
    }
 
    @Test
    public void testMethodOne() {
        long id = Thread.currentThread().getId();
        System.out.println("Sample test-method One. Thread id is: " + id);
    }
 
    @Test
    public void testMethodTwo() {
        long id = Thread.currentThread().getId();
        System.out.println("Sample test-method Two. Thread id is: " + id);
    }
 
    @AfterClass
    public void afterClass() {
        long id = Thread.currentThread().getId();
        System.out.println("After test-class. Thread id is: " + id);
    }
}