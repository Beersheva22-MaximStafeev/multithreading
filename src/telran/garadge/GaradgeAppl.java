package telran.garadge;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.IntStream;

public class GaradgeAppl {
	
	public static final int SLEEP_DURATION_PER_DAY = 1;
	private static final int DEFAULT_TIME_TO_WORK = 60 * 8 * 30 * 6; 
	// minutes/hour * hours/day * days/month * months 
	
	private static int nWorkers;
	private static int queueCapacity;
	private static double incomePerMinute;
	private static double salaryPerMinute;
	private static int timeToWork = DEFAULT_TIME_TO_WORK;
	
	private static LinkedBlockingQueue<Integer> queueOfRepairTimes;
	private static ArrayList<Worker> workers = new ArrayList<>();
	private static Car car;

	private static LinkedBlockingQueue<Integer> repairTimes = new LinkedBlockingQueue<>();
	private static LinkedBlockingQueue<Integer> finishedRepairTimes = new LinkedBlockingQueue<>();
	private static int sumIdleTime;
	private static int sumFinishedRepairTimes;
	private static int processedCars;

	public static void main(String[] args) {
		initValues();
		startAndJoinThreads();
		printStatistic();
	}

	private static void startAndJoinThreads() {
		workers.stream().forEach(Worker::start);
		car.start();
		try {
			car.join();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		workers.stream().forEach(t -> {
			try {
				t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
	}

	private static void initValues() {
//		StandardInputOutput io = new StandardInputOutput();
//		nWorkers = io.readInt("Enter number of workers", "From 1", 1, Integer.MAX_VALUE);
//		queueCapacity = io.readInt("Enter queue Capacity", "Enter from 1", 1, Integer.MAX_VALUE);
		nWorkers = 24;
		queueCapacity = 10;
		incomePerMinute = 100d / 60;
		salaryPerMinute = 40d / 60;
		
		queueOfRepairTimes = new LinkedBlockingQueue<>(queueCapacity);
		System.out.println("Inited queue");
		IntStream.range(0, nWorkers).forEach(i -> workers.add(new Worker()));
		car = new Car();
	}
	
	public static LinkedBlockingQueue<Integer> getQueue() {
		if (queueOfRepairTimes == null) {
			throw new IllegalArgumentException("queue is null in main app");
		}
		return queueOfRepairTimes;
	}
	
	public static LinkedBlockingQueue<Integer> getRepairTimes() {
		return repairTimes;
	}
	
	public static LinkedBlockingQueue<Integer> getFinishedRepairTimes() {
		return finishedRepairTimes;
	}
	
	public static int getTimeToWork() {
		return timeToWork;
	}

	private static void printStatistic() {
//		System.out.println("Finished!!");
		workers.forEach(worker -> { 
//			System.out.printf("Worker %s idle for %s mins, %s%%\n", worker, worker.idleTime, worker.idleTime * 100 / DEFAULT_TIME_TO_WORK);
			sumIdleTime += worker.getIdleTime();
			processedCars += worker.getCarsProcessed();
		});
		finishedRepairTimes.forEach(el -> sumFinishedRepairTimes += el);
		System.out.printf("- sum of idle time: %s min\n", sumIdleTime);
		System.out.printf("- avg idle time: %s min\n- avg %% idle time: %s%%\n", sumIdleTime / nWorkers, sumIdleTime * 100 / nWorkers / DEFAULT_TIME_TO_WORK);
		System.out.printf("nWorkers: %s\nQueue limit: %s\n", nWorkers, queueCapacity);
		System.out.printf("All cars: %s\n", processedCars + car.getCarsRejected() + queueOfRepairTimes.size());
		System.out.printf("- processed cars: %s\n- rejected cars: %s\n", processedCars, car.getCarsRejected());
		System.out.printf("- cars stay in queue: %s\n", queueOfRepairTimes.size());
		System.out.printf("Avg repair time: %s\n", repairTimes.stream().mapToInt(el -> el).sum() / repairTimes.size());
		System.out.printf("Money:\n- salary / minute\t: %,15.2f\n- income / minute\t: %,15.2f\n- salary total\t\t: %,15.2f\n- income total\t\t: %,15.2f\n- profit\t\t: %,15.2f\n",
				salaryPerMinute, incomePerMinute, 
				nWorkers * DEFAULT_TIME_TO_WORK * salaryPerMinute,
				sumFinishedRepairTimes * incomePerMinute,
				sumFinishedRepairTimes * incomePerMinute - nWorkers * DEFAULT_TIME_TO_WORK * salaryPerMinute);
	}
}
