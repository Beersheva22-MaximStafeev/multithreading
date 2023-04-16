package telran.cockroach;

import java.util.Random;

public class Cockroach extends Thread {
	
	private Race race;
	private long workTime;
	private String name;
	
	public Cockroach(Race race, String name) {
		this.race = race;
		this.name = name;
	}
	
	@Override
	public void run() {
		Random rnd = new Random();
		long startTime = System.currentTimeMillis();
		for (int i = 0; i < race.nRuns(); i++) {
			try {
				if (race.writeOutput()) {
					System.out.printf("Cockroach [%s] steps %s %n", name, i);
				}
				sleep(rnd.nextInt(race.minMs(), race.maxMs()));
			} catch (InterruptedException e) {
			}
		}
		workTime = System.currentTimeMillis() - startTime;
	}
	
	public long getWorkTime() {
		return workTime;
	}
}
