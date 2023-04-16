package telran.multithreading;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class PrinterController {

	public static void main(String[] args) throws InterruptedException {
		Printer p1 = new Printer("#", 100);
		Printer p2 = new Printer("*", 100);
		Instant start = Instant.now();
		p1.start();
		p2.start();
		p1.join();
		p2.join();
		System.out.printf("\nrunning time is %d milliseconds\n", ChronoUnit.MILLIS.between(start, Instant.now()));

		// Outputs of threads without sleep:
		// #####***********#####################################################***************************************##########################################**************************************************
		// **************##############################################################################################**######************************************************************************************
		// ****************************************************************************##*******#######################***************####################################################**#######################
	}

}
