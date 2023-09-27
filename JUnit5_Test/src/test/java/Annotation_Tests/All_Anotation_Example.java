/****************************************************************************
* Copyright 2021 (C) Andrey Tokmakov
* All_Anotation_Example.java class
*
* @name    : All_Anotation_Example.java
* @author  : Tokmakov Andrey
* @version : 1.0
* @since   : Jan 8, 2021
****************************************************************************/

package Annotation_Tests;


import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class All_Anotation_Example {

    @BeforeAll
    static void setup(){
        System.out.println("@BeforeAll executed");
    }
     
    @BeforeEach
    void setupThis(){
        System.out.println("\n  @BeforeEach executed");
    }

    @Tag("DEV")
    @DisplayName("Test #1 Name")
    @Test 
    void test1() 
    {
        System.out.println("     Running test1");
    }
     
    @Tag("PROD")
    @Disabled
    @DisplayName("Test #2 Name")
    @Test
    void test2() {
        System.out.println("     Running test2");
    }
    
    @Tag("DEV")
    @DisplayName("Test #3 Name")
    @Test
    void test3() {
        System.out.println("     Running test3");
    }
     
    @AfterEach
    void tearThis(){
        System.out.println("  @AfterEach executed");
    }
     
    @AfterAll
    static void tear(){
        System.out.println("\n@AfterAll executed");
    }
    
    
	@Nested
	@DisplayName("Some_Nested_Tests_Herer")
	class FindOne {
		
	    @Tag("DEV")
	    @DisplayName("Nested Test #1 Name")
	    @Test 
		void nestedTest1() {
	        System.out.println("     Running nestedTest1");
		}

	    @Tag("DEV")
	    @DisplayName("Nested Test #2 Name")
	    @Test 
		void nestedTest2() {
	        System.out.println("     Running nestedTest2");
		}
	}
}
