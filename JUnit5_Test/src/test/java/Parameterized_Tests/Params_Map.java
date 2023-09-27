/****************************************************************************
* Copyright 2021 (C) Andrey Tokmakov
* Params_Map.java class
*
* @name    : Params_Map.java
* @author  : Tokmakov Andrey
* @version : 1.0
* @since   : Jan 16, 2021
****************************************************************************/

package Parameterized_Tests;

import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.platform.commons.util.StringUtils;

class StringCustomArgumentsProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
    	return Stream.of(Arguments.of((String) null), 
    					 Arguments.of(""), 
    					 Arguments.of("   ") 
        );
    }
}


public class Params_Map {
	
	private static Stream<Arguments> provideStringsForIsBlank() {
	    return Stream.of(
	      Arguments.of(null, true),
	      Arguments.of("", true),
	      Arguments.of("  ", true),
	      Arguments.of("not blank", false)
	    );
	}
	
	@ParameterizedTest
	@MethodSource("provideStringsForIsBlank")
	void isBlank_ShouldReturnTrueForNullOrBlankStrings(String input, boolean expected) {
		Assertions.assertEquals(expected, StringUtils.isBlank(input));
	}
	
	@ParameterizedTest
	@ArgumentsSource(StringCustomArgumentsProvider.class)
	void customArgumentsProviderTests(String input) {
		System.out.println("input = [" + input + "]");
		Assertions.assertTrue(StringUtils.isBlank(input));
	}
}
