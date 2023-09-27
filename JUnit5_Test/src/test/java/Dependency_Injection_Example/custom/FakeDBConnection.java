/****************************************************************************
* Copyright 2020 (C) Andrey Tokmakov
* FakeDBConnection class
*
* @name    : FakeDBConnection.java
* @author  : Tokmakov Andrey
* @version : 1.0
* @since   : November 23, 2020
****************************************************************************/ 

package Dependency_Injection_Example.custom;

import java.util.Arrays;
import java.util.List;

public class FakeDBConnection {
	public List<String> getAllUserNames() {
		return Arrays.asList("peterm", 
				             "mikek",
				             "johna",
				             "anandv");
	}
}