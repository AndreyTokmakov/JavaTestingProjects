/****************************************************************************
* Copyright 2021 (C) Andrey Tokmakov
* CustomTagsTests.java class
*
* @name    : CustomTagsTests.java
* @author  : Tokmakov Andrey
* @version : 1.0
* @since   : Jan 9, 2021
****************************************************************************/

package Tags.custom_tags;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;

@Tags(value = {@Tag("regression"), @Tag("payment_module")})
public class CustomTagsTests {
	
	@Tag("accessability")
	@Test
	void api_test1() {
		System.out.println("api_test1()");
	}
	  
	@PerformanceApiTag
	@Test
	void performance_api_test1() {
	    System.out.println("performance_api_test1()");
	}
	  
	@Tag("load")
	@Test
	void load_test1() {
	    System.out.println("load_test1()");
	}
	  
	@PerformanceApiTag
	@Test
	void performance_api_test2() {
	    System.out.println("performance_api_test2()");
	}
}
	