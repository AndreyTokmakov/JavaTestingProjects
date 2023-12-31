/****************************************************************************
* Copyright 2020 (C) Andrey Tokmakov
* Valuesource_Test class
*
* @name    : Valuesource_Test.java
* @author  : Tokmakov Andrey
* @version : 1.0
* @since   : November 22, 2020
****************************************************************************/ 

package Parameterized_Tests;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

public class ValueSource_Test {
	
	@ParameterizedTest
	@ValueSource(ints = {8,4,2,6,10})
    void test_int_arrays(int arg) {
    	System.out.println("arg => "+arg);
        assertTrue(arg % 2 == 0);
    }  
    
    @ParameterizedTest(name="#{index} - Test with Argument={0}")
    @ValueSource(strings = {"Peter King", "Arthur King", "Martin King"})
    void test_string_arrays(String arg) {
    	String searchKey = "King";
    	
    	System.out.println("arg => "+arg);
        assertTrue(arg.contains(searchKey));
    }  
    
    @ParameterizedTest(name="#{index} - Test with Argument={0}")
    @NullSource
    void test_null_source(String arg) {
    	
    	System.out.println("arg => "+arg);
        assertTrue(arg == null);
    } 
    
    @ParameterizedTest(name="#{index} - Test with Argument={0}")
    @EmptySource
    void test_empty_source(String arg) {
    	
    	System.out.println("arg => "+arg);
        assertTrue(arg.isEmpty());
    } 
    
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = { " ", "   ", "\t", "\n" })
    void test_nullAndEmpty_source(String arg) {
        assertTrue(arg == null || arg.trim().isEmpty());
    }
}
