/****************************************************************************
* Copyright 2021 (C) Andrey Tokmakov
* ClassATest.java class
*
* @name    : ClassATest.java
* @author  : Tokmakov Andrey
* @version : 1.0
* @since   : Jan 9, 2021
****************************************************************************/

package Tags.tests1;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

@Tag("development")
public class ClassATest
{
    @Test
    @Tag("development")
    @Tag("production")
    void Test1(TestInfo testInfo) {
    	System.out.println("   Running test Test1");
    }
    
    
}