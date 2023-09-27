/****************************************************************************
* Copyright 2020 (C) Andrey Tokmakov
* GuiceTest.java class
*
* @name    : GuiceTest.java
* @author  : Tokmakov Andrey
* @version : 1.0
* @since   : Dec 23, 2020
****************************************************************************/

package DI_Guice;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import org.testng.Assert;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;


@Guice(modules = {GuiceModule.class})
public class GuiceTest extends Assert {

	@Inject
	@Named("guice-string-0")
	private String word0;

	@Inject
	@Named("guice-string-1")
	  private String word1;

	@Test
	public void testService() {
		final String actual = word0 + word1;
	    assertEquals(actual, "Hello, World!!!");
	}
}