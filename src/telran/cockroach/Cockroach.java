package telran.cockroach;

@FunctionalInterface
public interface Cockroach {
	public String cockroachRun(int nCockroach, int nRuns, boolean printRuns);
}
