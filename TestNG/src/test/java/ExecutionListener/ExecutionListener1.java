/****************************************************************************
* Copyright 2020 (C) Andrey Tokmakov
* ExecutionListener1.java class
*
* @name    : ExecutionListener1.java
* @author  : Tokmakov Andrey
* @version : 1.0
* @since   : Dec 23, 2020
****************************************************************************/

package ExecutionListener;

import org.testng.IExecutionListener;

public class ExecutionListener1 implements IExecutionListener {
    private long startTime;
 
    @Override
    public void onExecutionStart() {
        startTime = System.currentTimeMillis();
        System.out.println("TestNG is going to start");     
    }
 
    @Override
    public void onExecutionFinish() {
        System.out.println("TestNG has finished, took around " + (System.currentTimeMillis() - startTime) + "ms");
    }
}