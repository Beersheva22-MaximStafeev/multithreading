package telran.multithreading.task3;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.IntStream;

public class ListOperationsController {

	private static final int N_NUMBERS = 100_000;
	private static final int N_THREADS = 1000;
	private static final int UPDATE_PROB = 50;
	private static final int N_RUNS = 1000;

	public static void main(String[] args) {
		System.out.println("Start");
		Integer[] numbers = new Integer[N_NUMBERS];
		Arrays.fill(numbers, 100);
		List<Integer> list = new LinkedList<>(Arrays.asList(numbers));
		ListOperations[] operations = new ListOperations[N_THREADS];
		
		ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
		Lock readLock = lock.readLock();
		Lock writeLock = lock.writeLock();
		AtomicInteger count = new AtomicInteger(0);
		
		IntStream.range(0, N_THREADS).forEach(i -> {
			operations[i] = new ListOperations(UPDATE_PROB, list, N_RUNS, readLock, writeLock, count);
			operations[i].start();
		});
		Arrays.stream(operations).forEach(t -> {
			try {
				t.join();
			} catch (InterruptedException e) {
				// TO DO Auto-generated catch block
				e.printStackTrace();
			}
		});
		System.out.println("Total number of itterations for waiting lock " + count);
	}
}
