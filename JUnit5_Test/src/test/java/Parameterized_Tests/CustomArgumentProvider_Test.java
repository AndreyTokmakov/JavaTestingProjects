/****************************************************************************
* Copyright 2020 (C) Andrey Tokmakov
* CustomArgumentProvider_Testclass
*
* @name    : CustomArgumentProvider_Test.java
* @author  : Tokmakov Andrey
* @version : 1.0
* @since   : November 22, 2020
****************************************************************************/ 

package Parameterized_Tests;

import java.util.List;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import Parameterized_Tests.custom.CustomArgumentProvider;

public class CustomArgumentProvider_Test {
    @ParameterizedTest
    @ArgumentsSource(CustomArgumentProvider.class)
    void testWith_implicit_conversion(String str, int num, List<String> list) {
    	System.out.println("str => "+str+"; num =>"+num+"; list => "+list);
    	assertTrue(str.length() >= 0);
        assertTrue(num >=1 && num <=3);
        assertTrue(list.size() >= 1);
    }    
}
