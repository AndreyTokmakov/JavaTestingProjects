/****************************************************************************
* Copyright 2020 (C) Andrey Tokmakov
* CounterClass class
*
* @name    : CounterClass.java
* @author  : Tokmakov Andrey
* @version : 1.0
* @since   : November 30, 2020
****************************************************************************/

import java.util.concurrent.atomic.AtomicLong;

public class CounterClass {
	public AtomicLong counter = new AtomicLong(0); 
	
	public void incrementCounter() {
		counter.incrementAndGet();
	}
	
	public long getCounter() {
		return this.counter.get();
	}
}