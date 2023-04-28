package telran.cockroach;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//public record Race(int nThreads, int nRuns, boolean writeOutput, int minMs, int maxMs) {} ;
public class Race {
	private int nThreads;
	private int nRuns;
	private boolean writeOutput;
	private int minMs;
	private int maxMs;
	public AtomicInteger rank = new AtomicInteger(0);
	public ArrayList<Thread> rankingTable = new ArrayList<>();
	public Object mutex = new Object();
	public Lock lock = new ReentrantLock(true);
	
	public Race(int nThreads, int nRuns, boolean writeOutput, int minMs, int maxMs) {
		super();
		this.nThreads = nThreads;
		this.nRuns = nRuns;
		this.writeOutput = writeOutput;
		this.minMs = minMs;
		this.maxMs = maxMs;
	}
	
	public int nThreads() {
		return nThreads;
	}
	public int nRuns() {
		return nRuns;
	}
	public boolean writeOutput() {
		return writeOutput;
	}
	public int minMs() {
		return minMs;
	}
	public int maxMs() {
		return maxMs;
	}
	
}