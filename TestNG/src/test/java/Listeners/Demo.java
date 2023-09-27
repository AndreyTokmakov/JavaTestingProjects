/****************************************************************************
* Copyright 2020 (C) Andrey Tokmakov
* Demo.java class
*
* @name    : Demo.java
* @author  : Tokmakov Andrey
* @version : 1.0
* @since   : Dec 23, 2020
****************************************************************************/

package Listeners;

import org.testng.SkipException;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.Assert;

@Listeners(ListenersTestNG.class)
public class Demo {

	@Test  //Success Test
	public void CloseBrowser() {
		//Reporter.log("Driver Closed After Testing");
	}
	
	@Test //Failed Test
	public void OpenBrowser() {
		// Assert.assertEquals(originalTitle, expectedTitle, "Titles of the website do not match");
	}
	
	private int i = 1;

	@Test (successPercentage = 60, invocationCount = 3) //Failing Within Success
	public void AccountTest() {
			if(i < 2)
				Assert.assertEquals(i , i);
		i++;
	}
	
	@Test  // Skip Test
	public void SkipTest() {
		throw new SkipException("Skipping The Test Method ");
	}
}