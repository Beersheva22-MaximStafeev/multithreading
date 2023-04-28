package telran.multithreading;

public class TimerTestAppl {
	public static void main(String[] args) throws InterruptedException {
		Timer timer = new Timer();
		timer.start();
		Thread.sleep(2_000);
		timer.interrupt();
		Thread.sleep(2_000);
		Thread mainThread = Thread.currentThread();
		mainThread.interrupt();
		mainThread.join();
	}
}
