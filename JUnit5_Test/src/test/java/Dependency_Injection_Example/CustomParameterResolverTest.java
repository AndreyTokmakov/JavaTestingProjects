/****************************************************************************
* Copyright 2020 (C) Andrey Tokmakov
* CustomParameterResolverTest class
*
* @name    : CustomParameterResolverTest.java
* @author  : Tokmakov Andrey
* @version : 1.0
* @since   : November 22, 2020
****************************************************************************/ 

package Dependency_Injection_Example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import Dependency_Injection_Example.custom.FakeDBConnection;
import Dependency_Injection_Example.custom.FakeDBParameterResolver;
import Dependency_Injection_Example.custom.FakeDBParameterResolver.FakeDB;

@ExtendWith(FakeDBParameterResolver.class)
public class CustomParameterResolverTest {

	@Test
	void injectFakeDbTest(@FakeDB FakeDBConnection connection) {
		
		// connection.getAllUserNames().forEach(System.out::println);
		
		Assertions.assertTrue(connection.getAllUserNames().contains("peterm"));
		Assertions.assertTrue(connection.getAllUserNames().contains("johna"));
	}
}
