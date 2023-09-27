/****************************************************************************
* Copyright 2020 (C) Andrey Tokmakov
* TestNG tests enabling/disabling demo
*
* @name    : DisableTestDemo.java
* @author  : Tokmakov Andrey
* @version : 1.0
* @since   : November 18, 2020
****************************************************************************/ 

package Disable_Ignore_Tests;

import org.testng.annotations.Test;
import org.testng.Assert;

public class DisableTestDemo 
{
    @Test(enabled = true)
    public void test1() {
        System.out.println("Test method one.");
    }
 
    @Test(enabled = false)
    public void test2() {
        System.out.println("Test method two.");
    }
 
    @Test
    public void test3() {
        System.out.println("Test method three.");
    }

    @Test(description = "This test should fail")
    public void shouldFail() {
        Assert.fail("Failure");
    }
}