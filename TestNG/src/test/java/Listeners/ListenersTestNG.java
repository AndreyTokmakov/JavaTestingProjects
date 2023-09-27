/****************************************************************************
* Copyright 2020 (C) Andrey Tokmakov
* ListenersTestNG.java class
*
* @name    : ListenersTestNG.java
* @author  : Tokmakov Andrey
* @version : 1.0
* @since   : Dec 23, 2020
****************************************************************************/

package Listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ListenersTestNG implements ITestListener {
	
	@Override
	public void onStart(ITestContext context) { 
		System.out.println("onStart method started");
	}
 
	@Override
	public void onFinish(ITestContext context) {
		System.out.println("onFinish method started");
	}
 
	@Override
	public void onTestStart(ITestResult result) {
		System.out.println("New Test Started" +result.getName());
	}
 
	@Override
	public void onTestSuccess(ITestResult result) {
		System.out.println("onTestSuccess Method" +result.getName());
	}
 
	@Override
	public void onTestFailure(ITestResult result) {
		System.out.println("onTestFailure Method" +result.getName());
	}
	
	@Override
	public void onTestSkipped(ITestResult result) {
		System.out.println("onTestSkipped Method" +result.getName());
	}
 
	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		System.out.println("onTestFailedButWithinSuccessPercentage" +result.getName());
	}
}