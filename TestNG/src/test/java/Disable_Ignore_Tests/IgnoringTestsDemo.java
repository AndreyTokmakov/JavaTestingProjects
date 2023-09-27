/****************************************************************************
* Copyright 2020 (C) Andrey Tokmakov
* IgnoringTestsDemo.java class
*
* @name    : IgnoringTestsDemo.java
* @author  : Tokmakov Andrey
* @version : 1.0
* @since   : Dec 21, 2020
****************************************************************************/

package Disable_Ignore_Tests;

import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
 
@Ignore
public class IgnoringTestsDemo {
	 
    @Test
    public void testMethod1() {
    }
 
    @Test
    public void testMethod2() {
    }
}
