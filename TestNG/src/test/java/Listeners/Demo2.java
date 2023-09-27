/****************************************************************************
* Copyright 2020 (C) Andrey Tokmakov
* Demo2.java class
*
* @name    : Demo2.java
* @author  : Tokmakov Andrey
* @version : 1.0
* @since   : Dec 23, 2020
****************************************************************************/

package Listeners;

import org.testng.Assert;
import org.testng.annotations.Test;

public class Demo2 extends Listeners2 {
	@Test
	public void firstMethod() {
		Assert.assertTrue(true);
	}
	  
	@Test
	public void secondMethod() {
		Assert.assertTrue(false);
	}
	  
	@Test(dependsOnMethods = {"firstMethod"})
	public void thirdMethod() {
		Assert.assertTrue(true);
	}
}