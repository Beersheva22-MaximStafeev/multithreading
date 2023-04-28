package telran.multithreading.kolhoz;

import java.util.concurrent.atomic.AtomicLong;

public class TruckAtom extends Thread {
	private int load;
	private static AtomicLong elevator1 = new AtomicLong();
	private static AtomicLong elevator2 = new AtomicLong();
	private int nRuns;
	
	@Override
	public void run() {
		for (int i = 0; i < nRuns; i++) {
			loadElevator1(load);
			loadElevator2(load);
		}
	}
	
	public TruckAtom(int load, int nRuns) {
		this.load = load;
		this.nRuns = nRuns;
	}

	private static void loadElevator1(long load) {
		elevator1.addAndGet(load);
	}

	private static void loadElevator2(long load) {
		elevator2.addAndGet(load);		
	}
	
	public static long  getElevator1() {
		return elevator1.get();
	}

	public static long getElevator2() {
		return elevator2.get();
	}
}
