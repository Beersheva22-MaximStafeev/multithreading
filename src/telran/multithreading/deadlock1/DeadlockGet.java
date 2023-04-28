package telran.multithreading.deadlock1;

public class DeadlockGet {
	
	public static Object mutex1 = new Object();
	public static Object mutex2 = new Object();
	
	public static void gotoSleep() {
		try {
			Thread.sleep(1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static class Thread1 extends Thread {
		@Override
		public void run() {
			synchronized (mutex1) {
				System.out.println("Thread1 got mutex1");
				gotoSleep();
				gotoSleep();
				System.out.printf("Thread1: mutex1 locked: %s, mutex2 locked: %s%n", Thread.holdsLock(mutex1), Thread.holdsLock(mutex2));
				gotoSleep();
				gotoSleep();
				gotoSleep();
				gotoSleep();
				synchronized (mutex2) {
					System.out.println("Thread1 got mutex2 and finished");
				}
			}
		}
		
	}
	
	public static class Thread2 extends Thread {
		@Override
		public void run() {
			gotoSleep();
			System.out.printf("Thread2: mutex1 locked: %s, mutex2 locked: %s%n", Thread.holdsLock(mutex1), Thread.holdsLock(mutex2));
			synchronized (mutex2) {
				System.out.println("Thread2 got mutex2");
				gotoSleep();
				synchronized (mutex1) {
					System.out.println("Thread2 got mutex1 and finished");
				}
			}			
		}
		
	}
	
	public static void main(String[] args) throws Exception {
		Thread thread1 = new Thread1();
		Thread thread2 = new Thread2();
		thread1.start();
		thread2.start();
		thread1.join();
		thread2.join();
		System.out.println("No deadlock");
	}
}
