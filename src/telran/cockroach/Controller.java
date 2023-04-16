package telran.cockroach;

import telran.view.*;

public class Controller {
	
	private CockroachRun cockroachRun;
	
	public Controller(CockroachRun cockroachRun) {
		this.cockroachRun = cockroachRun;
	}
	
	public Menu getMenu() {
		return new Menu("cockroach runs",
				Item.of("Run", io -> run(io)),
				Item.of("Exit", io -> {io.writeLine("Good bye!");}, true));
	}

	private void run(InputOutput io) {
		int nRuns = io.readInt("Enter count of itterations from 100 to 1000", "Enter from 100 to 1000", 100, 1000);
		int nCockroach = io.readInt("Enter num of cockroach from 2 to 10", "Enter from 2 to 10", 2, 10);
		boolean printRuns = io.readInt("Enter 0 for silent 1 for noise", "0 / 1", 0, 1) == 1;
		String res = cockroachRun.cockroachRun(nCockroach, nRuns, printRuns);
		io.writeLine(res);
	}
}
