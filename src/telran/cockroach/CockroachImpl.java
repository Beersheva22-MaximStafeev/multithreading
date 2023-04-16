package telran.cockroach;

public class CockroachImpl implements CockroachRun {

	private int minMs;
	private int maxMs;
	
	public CockroachImpl(int minMs, int maxMs) {
		this.minMs = minMs;
		this.maxMs = maxMs;
	}
	
	@Override
	public String cockroachRun(int nCockroach, int nRuns, boolean printRuns) {
		Race race = new Race(nCockroach, nRuns, printRuns, minMs, maxMs);
		Cockroach[] cockroachs = new Cockroach[nCockroach];
		for (int i = 0; i < nCockroach; i++) {
			cockroachs[i] = new Cockroach(race, "c" + i);
		}
		for (Cockroach c: cockroachs) {
			c.start();
		}
		for (Cockroach c: cockroachs) {
			try {
				c.join();
			} catch (InterruptedException e) {
			}
		}
		long minTime = Long.MAX_VALUE;
		int cockroachWinner = 0;
		for (int i = 0; i < nCockroach; i++) {
			long curWorkTime = cockroachs[i].getWorkTime();
			if (curWorkTime < minTime) {
				minTime = curWorkTime;
				cockroachWinner = i;
			}
		}
		return String.format("The winner is cockroach #%s, his time: %sms", cockroachWinner + 1, minTime);
	}

}
