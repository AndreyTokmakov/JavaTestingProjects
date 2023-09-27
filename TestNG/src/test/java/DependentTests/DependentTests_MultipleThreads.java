/****************************************************************************
* Copyright 2021 (C) Andrey Tokmakov
* DependentTests_MultipleThreads.java class
*
* @name    : DependentTests_MultipleThreads.java
* @author  : Tokmakov Andrey
* @version : 1.0
* @since   : Feb 27, 2021
****************************************************************************/

package DependentTests;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class DependentTests_MultipleThreads {
	
	@BeforeClass
	void setUp() throws Exception {
		System.out.println("==== Tests setUp() ====");
	}

	@AfterClass
	void tearDown() throws Exception {
		System.out.println("==== Tests tearDown() ====");
	}
	
    @Test(enabled = false)
    public void Test1() {
        System.out.println("Test1");
        // sleep(1000);
    }
    
    @Test(enabled = false)
    public void Test2() {
        System.out.println("Test2");
    }
    
    @Test(threadPoolSize = 2, invocationCount = 20, invocationTimeOut = 10000, enabled = false)
    public void Test3() {
        System.out.println("Test3: " + Thread.currentThread().getName() + "." + Thread.currentThread().getId());
        sleep(333);
    }
    

    @Test(dependsOnMethods = {"Test1"})
    public void Test4() {
        System.out.println("Test4");
    }
    
    private void sleep(int milliseconds) {
    	try {
			TimeUnit.MILLISECONDS.sleep(milliseconds);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
