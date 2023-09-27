package Tests;

import org.testng.Assert;
import org.testng.annotations.*;

public class SimpleTest {
 
	@BeforeClass
	public void setUp() {
		System.out.println("setUp");
	}
	
	@AfterClass
	public void tearDown() {
		System.out.println("tearDown");
	}
	
	@BeforeTest
	public void beforeEachTest () {
		System.out.println(" << Call before each test >>");
	}
	
	@AfterTest
	public void afterEachTest () {
		System.out.println(" << Call after each test >>");
	}
	
	@BeforeMethod
	public void beforeEachTestMetchod () {
		System.out.println("     --> Call before each test method **");
	}
	
	@AfterMethod
	public void afterEachTestMetchod () {
		System.out.println("     <-- Call after each test method **");
	}
	 
	@Test(groups = { "first" })
	public void FirstTest() {
		System.out.println("First test");
	}
	 
	@Test(groups = { "sends" })
	public void SecondTest() {
		System.out.println("Second test");
	}

	@Test(description = "This test should fail")
	public void shouldFail() {
		Assert.fail("Failure");
	}
}
