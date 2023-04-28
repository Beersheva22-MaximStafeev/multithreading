package telran.multithreading.task3;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;

public class ListOperations extends Thread {

	int probUpdate;
	List<Integer> list;
	int nRuns;
	Lock readLock;
	Lock writeLock;
	AtomicInteger count;

	public ListOperations(int probUpdate, List<Integer> list, int nRuns, Lock readLock, Lock writeLock, AtomicInteger count) {
		super();
		this.probUpdate = probUpdate;
		this.list = list;
		this.nRuns = nRuns;
		this.readLock = readLock;
		this.writeLock = writeLock;
		this.count = count;
	}

	@Override
	public void run() {
		for (int i = 0; i < nRuns; i++) {
			ThreadLocalRandom tlr = ThreadLocalRandom.current();
			if (tlr.nextInt(0, 100) < probUpdate) {
				updateList();
			} else {
				readList();
			}
		}
	}

	private void lock(Lock lock) {
		while (!lock.tryLock()) {
			lock.lock();
			count.incrementAndGet();
		}
	}

	private void readList() {
		lock(readLock);
		lock(readLock);
		try {
			int size = list.size();
			for (int i = 1; i < 100; i++) {
				list.get(size - 1);
			}
		} finally {
			readLock.unlock();
			readLock.unlock();
		}
	}

	private void updateList() {
		lock(writeLock);
		lock(writeLock);
		try {
			list.remove(0);
			list.remove(0);
			list.remove(0);
			list.add(100);
			list.add(101);
			list.add(102);
		} finally {
			writeLock.unlock();
			writeLock.unlock();
		}
	}

}
