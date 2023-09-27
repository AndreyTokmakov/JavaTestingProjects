/****************************************************************************
* Copyright 2020 (C) Andrey Tokmakov
* Static inner classes tests
*
* @name    : Static_Inner_Class.java
* @author  : Tokmakov Andrey
* @version : 1.0
* @since   : November 30, 2020
****************************************************************************/

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import static org.awaitility.Awaitility.*;

class WaiterTester {
	
	protected void sleep(int seconds) {
		try {
			TimeUnit.SECONDS.sleep(seconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	protected void Test() throws InterruptedException {
		AtomicLong counter = new AtomicLong(0);
		
		final Runnable incrementor = () -> {
			for (int i = 0; i < 10; i++) {
				counter.incrementAndGet();
				sleep(1);
			}
			System.out.println("counter = " + counter.get());
		};
		
		Thread thread = new Thread(incrementor);
		thread.start();
		// thread.join();
		
		await().until(() -> { 
			return 5 == counter.get();
		});
		
		System.out.println("counter = " + counter.get());
	}
}

public class SimpleTests {
	private final static WaiterTester tester = new WaiterTester();
	
	public static void main(String[] args) throws InterruptedException 
	{
		tester.Test();
	}
}
