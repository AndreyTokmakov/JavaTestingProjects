/****************************************************************************
* Copyright 2020 (C) Andrey Tokmakov
* Wait_and_AssertJ class
*
* @name    : Wait_and_AssertJ.java
* @author  : Tokmakov Andrey
* @version : 1.0
* @since   : December 01, 2020
****************************************************************************/

import java.util.concurrent.TimeUnit;
import org.assertj.core.api.Assertions;

import static org.awaitility.Awaitility.await;

class Wait_AssertJ_Tester {
	private CounterClass obj = new CounterClass();
	
	protected void sleep(int seconds) {
		try {
			TimeUnit.SECONDS.sleep(seconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	protected void Wait1_Test() throws InterruptedException
	{
		final Runnable incrementor = () ->
		{
			System.out.println("Thread started.");
			for (int i = 0; i < 20; i++) {
				obj.incrementCounter();
				sleep(1);
				System.out.println("counter = " + obj.getCounter());
			}
			System.out.println("Thread done. counter = " + obj.getCounter());
		};
		
		Thread thread = new Thread(incrementor);
		thread.start();
		// thread.join();
		
		await().atMost(5, TimeUnit.SECONDS).untilAsserted(
			() -> {
				long counter = obj.getCounter();
				System.out.println("Condition: " + counter);
				Assertions.assertThat(counter).isEqualTo(2);
			}
		);
		
		System.out.println("Waiting done. Counter = " + obj.getCounter());
	}
}

public class Wait_and_AssertJ {
	public static void main(String[] args) throws InterruptedException
	{
		Wait_AssertJ_Tester T = new Wait_AssertJ_Tester();
		T.Wait1_Test();
	}
}
