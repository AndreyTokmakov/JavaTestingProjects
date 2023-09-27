/****************************************************************************
* Copyright 2020 (C) Andrey Tokmakov
* TestClass.java class
*
* @name    : TestClass.java
* @author  : Tokmakov Andrey
* @version : 1.0
* @since   : Dec 23, 2020
****************************************************************************/

package ExecutionListener;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
 
public class TestClass {
    @BeforeSuite
    public void beforeSuite() {
        System.out.println("beforeSuite");
    }
     
    @Test
    public void t() {
        System.out.println("test");
    }
     
    @AfterSuite
    public void afterSuite() {
        System.out.println("afterSuite");
    }
}