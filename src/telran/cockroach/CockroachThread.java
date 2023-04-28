package telran.cockroach;

import java.util.Random;

public class CockroachThread extends Thread {
	
	private Race race;
	private long workTime;
	private long endMills;
	private String name;
	private int place;
	
	public CockroachThread(Race race, String name) {
		this.race = race;
		this.name = name;
	}
	
	@Override
	public void run() {
		Random rnd = new Random();
		long startTime = System.currentTimeMillis();
		for (int i = 0; i < race.nRuns(); i++) {
			if (race.writeOutput()) {
				System.out.printf("Cockroach [%s] steps %s %n", name, i);
			}
			try {
				sleep(rnd.nextInt(race.minMs(), race.maxMs()));
			} catch (InterruptedException e) {
			}
		}
		place = race.rank.addAndGet(1);
		
//		synchronized (race.mutex) {
//		if (race.lock.tryLock());
		race.lock.lock();
		try {
//			String res = String.format("%d. %s", race.rankingTable.size() + 1, name);
			race.rankingTable.add(this);
			endMills = System.currentTimeMillis();
		} finally {
			race.lock.unlock();
			
		}
		workTime = endMills - startTime;
	}
	
	public int getPlace() {
		return place;
	}
	
	public long getWorkTime() {
		return workTime;
	}
	
	public long getEndMills() {
		return endMills;
	}
	
	public String getCockroachName() {
		return name;
	}
}
