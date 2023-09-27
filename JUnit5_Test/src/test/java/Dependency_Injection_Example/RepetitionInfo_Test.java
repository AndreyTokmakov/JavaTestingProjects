/****************************************************************************
* Copyright 2020 (C) Andrey Tokmakov
* RepetitionInfo_Test class
*
* @name    : RepetitionInfo_Test.java
* @author  : Tokmakov Andrey
* @version : 1.0
* @since   : November 23, 2020
****************************************************************************/ 

package Dependency_Injection_Example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.TestInfo;
import Utilities.MathUtil;

public class RepetitionInfo_Test {
	
	@BeforeEach
    void beforeEach(TestInfo testInfo, RepetitionInfo repetitionInfo) {
        int currentRepetition = repetitionInfo.getCurrentRepetition();
        int totalRepetitions = repetitionInfo.getTotalRepetitions();
        String methodName = testInfo.getTestMethod().get().getName();
        
        System.out.println(String.format("About to execute repetition %d of %d for %s", //
                currentRepetition, totalRepetitions, methodName));
    }
	
	@RepeatedTest(3)
	void test_Add(RepetitionInfo repetitionInfo) {
		System.out.println("start test_Add() : "+repetitionInfo.getCurrentRepetition());
		Assertions.assertEquals(5, MathUtil.add(3, 2));
	}
	
	@AfterEach
	void afterEach(TestInfo testInfo, RepetitionInfo repetitionInfo) {
		int currentRepetition = repetitionInfo.getCurrentRepetition();
        int totalRepetitions = repetitionInfo.getTotalRepetitions();
        String methodName = testInfo.getTestMethod().get().getName();
        
        System.out.println(String.format("After completed execute repetition %d of %d for %s", //
                currentRepetition, totalRepetitions, methodName));
	}
}
