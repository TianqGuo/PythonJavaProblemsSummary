/**
The purpose of this class is to test concurrency and shows inaccuracy due to not threadSafe.

Answer Author: Tianquan Guo
Date: 4/18/2021
 */



package Problems;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MultiThreadsTest {
	private static int threadTotal = 200;
	private static int clientTotal = 5000;
	private static int count = 0;
	
	public static void main(String[] args) {
		ExecutorService exec = Executors.newCachedThreadPool();
		final Semaphore semaphore = new Semaphore(threadTotal);
		for (int i = 0; i < clientTotal; i++) {
			exec.execute(() -> {
				try {
					semaphore.acquire();
					count++;
					semaphore.release();
				} catch (Exception e) {
					e.printStackTrace();
					Logger logger = Logger.getAnonymousLogger();
					logger.log(Level.SEVERE,"an exception was thrown", e);
				}
			}
			);
		}
		exec.shutdown();
		System.out.println(count);
	}
}
