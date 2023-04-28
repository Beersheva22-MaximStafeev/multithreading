package telran.garadge;

//import telran.view.*;
import java.util.*;

public class GaradgeNoThread {
	
	private static class Vehicle {
		
		final int timeToRepare;
		private int leastTime;
		
		
		public Vehicle() {
			timeToRepare = getRandomTimeRepair();
			leastTime = timeToRepare;
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

		public static boolean itsTimeToAppear() {
			return new Random().nextInt(100) < 15;
		}
		
		public boolean isRepaired() {
			return leastTime <= 0;
		}
		
		public void processRepair() {
			leastTime--;
		}
	}
	
	private static class Worker {
		boolean isBusy = false;
		Vehicle vehicle = null;
		int idleTime = 0;
	}
	
	private static final int DEFAULT_TIME_TO_WORK = 60 * 8 * 30 * 6; 
		// minutes/hour * hours/day * days/month * months 
	private static int timeToWork = DEFAULT_TIME_TO_WORK;
	private static int nWorkers;
	private static int queueCapacity;
	private static int rejectedCars = 0;
	private static int processedCars = 0;
	private static LinkedList<Vehicle> queue;
	private static ArrayList<Worker> workers;
	private static LinkedList<Integer> repairTimes = new LinkedList<>();
	private static LinkedList<Integer> finishedRepairTimes = new LinkedList<>();
	private static int sumIdleTime = 0;
	private static int sumFinishedRepairTimes = 0;
	private static double incomePerMinute;
	private static double salaryPerMinute;

	public static void main(String[] args) {
		initValues();
		while (timeToWork-- > 0) {
			finishWork();
			getFromQueueToWork();
			generateAndPlaceToQueue();
			getFromQueueToWork();
		}
		finishWork();
		printStatistic();
	}

	private static void getFromQueueToWork() {
		List<Worker> freeWorkers;
		while (queue.size() > 0 && (freeWorkers = workers.stream().filter(worker -> !worker.isBusy).toList()).size() > 0) {
			Worker newWorker = freeWorkers.get(0);
			newWorker.vehicle = queue.remove();
			newWorker.isBusy = true;
		}
		
	}

	private static void finishWork() {
		workers.forEach(worker -> {
			if (worker.isBusy) {
				worker.vehicle.processRepair();
				if (worker.vehicle.isRepaired()) {
					worker.isBusy = false;
					finishedRepairTimes.addLast(worker.vehicle.timeToRepare);
					worker.vehicle = null;
					processedCars ++;
				}
			} else {
				worker.idleTime++;
			}
		});
		
	}

	private static void generateAndPlaceToQueue() {
		if (Vehicle.itsTimeToAppear()) {
			if (queue.size() < queueCapacity) {
				queue.add(new Vehicle());
				repairTimes.add(queue.peekLast().timeToRepare);
			} else {
				rejectedCars++;
			}
		}
	}

	private static void initValues() {
//		StandardInputOutput io = new StandardInputOutput();
//		nWorkers = io.readInt("Enter number of workers", "From 1", 1, Integer.MAX_VALUE);
//		queueCapacity = io.readInt("Enter queue Capacity", "Enter from 1", 1, Integer.MAX_VALUE);
		nWorkers = 24;
		queueCapacity = 10;
		incomePerMinute = 100d / 60;
		salaryPerMinute = 40d / 60;
		queue = new LinkedList<>(); 
		workers = new ArrayList<>();
		while (workers.size() < nWorkers) {
			workers.add(new Worker());
		}
	}

	private static void printStatistic() {
//		System.out.println("Finished!!");
		workers.forEach(worker -> worker.idleTime--);
		workers.forEach(worker -> { 
//			System.out.printf("Worker %s idle for %s mins, %s%%\n", worker, worker.idleTime, worker.idleTime * 100 / DEFAULT_TIME_TO_WORK);
			sumIdleTime += worker.idleTime;
		});
		finishedRepairTimes.forEach(el -> sumFinishedRepairTimes += el);
		System.out.printf("- sum of idle time: %s min\n", sumIdleTime);
		System.out.printf("- avg idle time: %s min\n- avg %% idle time: %s%%\n", sumIdleTime / nWorkers, sumIdleTime * 100 / nWorkers / DEFAULT_TIME_TO_WORK);
		System.out.printf("nWorkers: %s\nQueue limit: %s\n", nWorkers, queueCapacity);
		System.out.printf("All cars: %s\n", processedCars + rejectedCars + queue.size());
		System.out.printf("- processed cars: %s\n- rejected cars: %s\n", processedCars, rejectedCars);
		System.out.printf("- cars stay in queue: %s\n", queue.size());
		System.out.printf("Avg repair time: %s\n", repairTimes.stream().mapToInt(el -> el).sum() / repairTimes.size());
		System.out.printf("Money:\n- salary / minute\t: %,15.2f\n- income / minute\t: %,15.2f\n- salary total\t\t: %,15.2f\n- income total\t\t: %,15.2f\n- profit\t\t: %,15.2f\n",
				salaryPerMinute, incomePerMinute, 
				nWorkers * DEFAULT_TIME_TO_WORK * salaryPerMinute,
				sumFinishedRepairTimes * incomePerMinute,
				sumFinishedRepairTimes * incomePerMinute - nWorkers * DEFAULT_TIME_TO_WORK * salaryPerMinute);
	}

}
