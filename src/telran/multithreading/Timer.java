package telran.multithreading;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Timer extends Thread {
	private static final String DEFAULT_FORMAT_PATTERN = "HH:mm:ss.S(n)";
	private static final long DEFAULT_TIMEOUT = 1000;
	DateTimeFormatter dtf;
	long timeout;
	
	public Timer() {
		this(DEFAULT_FORMAT_PATTERN, DEFAULT_TIMEOUT);
	}
	
	public Timer(String formatPattern, long timeout) {
		dtf = DateTimeFormatter.ofPattern(formatPattern);
		this.timeout = timeout;
		setDaemon(true);
	}
	
	@Override
	public void run() {
		while (true) {
			System.out.println(LocalTime.now().format(dtf));
			try {
				sleep(timeout);
			} catch (InterruptedException e) {
				break;
			}
		}
		
	}
}
