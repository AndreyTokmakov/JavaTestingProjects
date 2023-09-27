/****************************************************************************
* Copyright 2020 (C) Andrey Tokmakov
* AssertsTests.java class
*
* @name    : AssertsTests.java
* @author  : Tokmakov Andrey
* @version : 1.0
* @since   : Dec 22, 2020
****************************************************************************/

package Asserts;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class AssertsTests {
	
    @Test
    public void test1() {
        System.out.println("Test1");
    }
    
    @Test(enabled = false)
    public void HardAssertion() {

    	String className = "HardAssertion";
    	
    	Assert.assertTrue(true == true);
    	Assert.assertEquals("HardAssert", "HardAssertion");
    	Assert.assertEquals(className, "HardAssertion");
    	System.out.println("Successfully passed!");
    }

    @Test(enabled = false)
    public void SoftAssertion() 
    {
    	SoftAssert softAssert = new SoftAssert();
    	String className = "SoftAssertion";
    	
    	softAssert.assertTrue(true == true);
    	softAssert.assertEquals("SoftAssert", "SoftAssertion");
    	softAssert.assertEquals(className, "SoftAssertion");
    	System.out.println("Last statement gets executed!");
    	softAssert.assertAll();
    }
    
    @Test
    public void AssertNotNull() 
    {
    	Object obj = null;
    	Assert.assertNotNull(obj, "Should not be null");
    }

    @Test
    public void testOK()
    {
        Object obj = null;
        Assert.assertNull(obj, "Should not be null");
    }
}
