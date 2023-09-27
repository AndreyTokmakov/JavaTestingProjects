/****************************************************************************
* Copyright 2020 (C) Andrey Tokmakov
* Wait for class params tests
*
* @name    : Wait_ForClassParams.java
* @author  : Tokmakov Andrey
* @version : 1.0
* @since   : November 30, 2020
****************************************************************************/

import java.util.concurrent.TimeUnit;
import org.hamcrest.Matchers;

import static org.awaitility.Awaitility.*;

class WaiterTester2 {
	
	private CounterClass obj = new CounterClass();
	
	protected void sleep(int seconds) {
		try {
			TimeUnit.SECONDS.sleep(seconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	protected void Wait1_Test() throws InterruptedException {

		final Runnable incrementor = () -> {
			for (int i = 0; i < 5; i++) {
				obj.incrementCounter();
				sleep(1);
			}
			System.out.println("counter = " + obj.getCounter());
		};
		
		Thread thread = new Thread(incrementor);
		thread.start();
		// thread.join();
		
		await().until(() -> { 
			return 2 == obj.getCounter();
		});
		
		System.out.println("Waiting done. Counter = " + obj.getCounter());
	}
	
	protected void Wait2_Test() throws InterruptedException {

		final Runnable incrementor = () -> {
			for (int i = 0; i < 5; i++) {
				obj.incrementCounter();
				sleep(1);
			}
			System.out.println("Thread done. counter = " + obj.getCounter());
		};
		
		Thread thread = new Thread(incrementor);
		thread.start();
		// thread.join();
		
		//await().untilAtomic(fieldIn(obj).ofType(AtomicLong.class).andWithName("counter"), Matchers.equalTo(2) );
		await().untilAtomic(obj.counter, Matchers.equalTo(2) );
		System.out.println("Waiting done. Counter = " + obj.getCounter());
	}
}

public class Wait_ForClassParams {	
	public static void main(String[] args) throws InterruptedException
	{
		WaiterTester2 T = new WaiterTester2();
		// T.Wait1_Test();
		 T.Wait2_Test();
	}
}
