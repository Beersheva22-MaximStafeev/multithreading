package telran.multithreading.kolhoz;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;

public class KolhozAtomAppl {

	private static final int N_TRUCKS = 1000;
	private static final int LOAD = 1;
	private static final int N_RUNS = 10000;

	public static void main(String[] args) {
		TruckAtom[] trucks = new TruckAtom[N_TRUCKS];
		startTrucks(trucks);
		Instant start = Instant.now();
		waitTrucks(trucks);
		displayResults(start);
		
	}

	private static void displayResults(Instant start) {
		System.out.printf("running time %d, elevator1 contains %d tons, elevator2 contains %d tons", 
				ChronoUnit.MILLIS.between(start, Instant.now()),
				TruckAtom.getElevator1(), TruckAtom.getElevator2() );
	}

	private static void waitTrucks(TruckAtom[] trucks) {
		Arrays.stream(trucks).forEach(t -> {
			try {
				t.join();
			} catch (InterruptedException e) {
				// TO DO Auto-generated catch block
				e.printStackTrace();
			}
		});
		
	}

	private static void startTrucks(TruckAtom[] trucks) {
		for (int i = 0; i < trucks.length; i++) {
			trucks[i] = new TruckAtom(LOAD, N_RUNS);
			trucks[i].start();
		}
	}

}
