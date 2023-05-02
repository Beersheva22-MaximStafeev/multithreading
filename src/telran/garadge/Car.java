package telran.garadge;

import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;

public class Car extends Thread {

	private int timeToWork;
	LinkedBlockingQueue<Integer> queue;
	private int carsRejected;

	public Car() {
		timeToWork = GaradgeAppl.getTimeToWork();
		queue = GaradgeAppl.getQueue();
		if (queue == null) {
			throw new IllegalArgumentException("Queue is null");
		}
	}

	@Override
	public void run() {
		while (timeToWork-- > 0) {
			if (timeToWork % 1000 == 0) {
				System.out.print(timeToWork + "\r");
			}
			if (itsTimeToAppear()) {
				int newTime = getRandomTimeRepair();
				GaradgeAppl.getRepairTimes().add(newTime);
				if (!queue.offer(newTime)) {
					carsRejected++;
				}
			}
			try {
				sleep(GaradgeAppl.SLEEP_DURATION_PER_DAY);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private int getRandomTimeRepair() {
		int typeRepair = new Random().nextInt(10);
		int timeRepare = 0;
		switch (typeRepair) {
			case 0:
			case 1:
			case 2:
				timeRepare = new Random().nextInt(30, 90);
				break;
			case 3:
			case 4:
			case 5:
			case 6:
			case 7:
				timeRepare = new Random().nextInt(91, 180);
				break;
			case 8:
			case 9:
				timeRepare = new Random().nextInt(181, 480);
				break;
		}
		return timeRepare;
	}

	private boolean itsTimeToAppear() {
		return new Random().nextInt(100) < 15;
	}
	
	public int getCarsRejected() {
		return carsRejected;
	}
}
