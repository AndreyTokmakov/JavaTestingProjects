/****************************************************************************
* Copyright 2020 (C) Andrey Tokmakov
* TestGroups.java class
*
* @name    : TestGroups.java
* @author  : Tokmakov Andrey
* @version : 1.0
* @since   : Dec 21, 2020
****************************************************************************/

package Groups;

import org.testng.annotations.Test;

public class TestGroups {
	
	@Test(groups = { "regression", "smoke" })
	public void testMethod1() {
	}
	 
	@Test(groups = { "regression", "smoke"} )
	public void testMethod2() {
	}
	 
	@Test(groups = { "regression" })
	public void testMethod3() {
	}
 
	@Test(groups = { "regression", "smoke" })
	public void testMethod4() {
	}
	
	@Test(groups = { "pefrormance", "smoke" })
	public void testMethod5() {
	}
	
	@Test(groups = { "pefrormance", "smoke" })
	public void testMethod6() {
	}
	
	
	
	@Test(groups = {"windows.checkintest"})
		public void testWindowsOnly() {
	}
	 
	@Test(groups = {"linux.checkintest"})
		public void testLinuxOnly() {
	}
	 
	@Test(groups = {"windows.functest"})
		public void testWindowsToo() {
	}
}