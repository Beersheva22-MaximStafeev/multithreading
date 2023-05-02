package telran.garadge;

import java.util.concurrent.LinkedBlockingQueue;

public class Worker extends Thread {

	private int idleTime;
	private boolean isBusy;
	
	private int timeToWork;
	LinkedBlockingQueue<Integer> queue;
	private Integer currentWork;
	private int currentWorkSave;
	private int carsProcessed;
	
	
	public Worker() {
		timeToWork = GaradgeAppl.getTimeToWork();
		queue = GaradgeAppl.getQueue();
	}
	
	@Override
	public void run() {
		while (timeToWork-- > 0) {
			if (isBusy) {
				currentWork--;
				if (currentWork == 0) {
					isBusy = false;
					carsProcessed++;
					GaradgeAppl.getFinishedRepairTimes().add(currentWorkSave);
				}
			} else {
				currentWork = queue.poll();
				if (currentWork != null) {
					currentWorkSave = currentWork;
					isBusy = true;
				} else {
					idleTime++;
				}
			}
			try {
				sleep(GaradgeAppl.SLEEP_DURATION_PER_DAY);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public int getIdleTime() {
		return idleTime;
	}

	public int getCarsProcessed() {
		return carsProcessed;
	}
}
