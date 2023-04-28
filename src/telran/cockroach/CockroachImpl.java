package telran.cockroach;

import java.util.Arrays;

public class CockroachImpl implements Cockroach {

	private int minMs;
	private int maxMs;
	
	public CockroachImpl(int minMs, int maxMs) {
		this.minMs = minMs;
		this.maxMs = maxMs;
	}
	
	@Override
	public String cockroachRun(int nCockroach, int nRuns, boolean printRuns) {
		Race race = new Race(nCockroach, nRuns, printRuns, minMs, maxMs);
		CockroachThread[] cockroachs = new CockroachThread[nCockroach];
		for (int i = 0; i < nCockroach; i++) {
			cockroachs[i] = new CockroachThread(race, "c" + (i + 1));
		}
		long startMills = System.currentTimeMillis();
		for (CockroachThread c: cockroachs) {
			c.start();
		}
		for (CockroachThread c: cockroachs) {
			try {
				c.join();
			} catch (InterruptedException e) {
			}
		}

		int cockroachWinner = 0;
		while (cockroachWinner < nCockroach && cockroachs[cockroachWinner].getPlace() != 1) {
			cockroachWinner++;			
		}
		long minTime = cockroachs[cockroachWinner].getWorkTime();
		String textRes = String.format("The winner is cockroach #%s, name: %s, his time: %sms%n", cockroachWinner + 1, cockroachs[cockroachWinner].getCockroachName(), minTime);

		int[] place = {1};
		String textRes1 = Arrays.stream(cockroachs)
			.sorted((a, b) -> Long.compare(a.getPlace(), b.getPlace()))
			.map(a -> String.format("%s. Name: %s, time: %s%n", place[0]++ ,a.getCockroachName(), a.getEndMills() - startMills))
			.reduce(String::concat).get();
		return textRes + textRes1;
	}

}
