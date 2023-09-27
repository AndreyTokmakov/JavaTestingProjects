/****************************************************************************
* Copyright 2020 (C) Andrey Tokmakov
* ExecutionListener2.java class
*
* @name    : ExecutionListener2.java
* @author  : Tokmakov Andrey
* @version : 1.0
* @since   : Dec 23, 2020
****************************************************************************/

package ExecutionListener;

import org.testng.IExecutionListener;

public class ExecutionListener2 implements IExecutionListener {
 
    @Override
    public void onExecutionStart() {
        System.out.println("Notify by mail that TestNG is going to start");     
    }
 
    @Override
    public void onExecutionFinish() {
        System.out.println("Notify by mail, TestNG is finished");
    }
}