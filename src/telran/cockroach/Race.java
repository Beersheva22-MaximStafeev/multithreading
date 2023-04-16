package telran.cockroach;

public record Race(int nThreads, int nRuns, boolean writeOutput, int minMs, int maxMs) {} ;
