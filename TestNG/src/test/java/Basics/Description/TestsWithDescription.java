/****************************************************************************
* Copyright 2021 (C) Andrey Tokmakov
* TestsWithDescription.java class
*
* @name    : TestsWithDescription.java
* @author  : Tokmakov Andrey
* @version : 1.0
* @since   : Feb 27, 2021
****************************************************************************/

package Basics.Description;

import org.testng.annotations.Test;

public class TestsWithDescription {

    @Test(description="This text should be displayed some were, i hope.")
    public void test1() {
        System.out.println("Test1");
    }
    
}
