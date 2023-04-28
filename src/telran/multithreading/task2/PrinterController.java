package telran.multithreading.task2;

import java.util.Arrays;

public class PrinterController {
	
	private static final int PRINTER_COUNT = 3;
	private static final int AMOUNT = 100;
	private static final int PORTION = 99;

	public static void main(String[] args) {
		Printer[] printers = new Printer[PRINTER_COUNT];
		
		for (int i = 0; i < PRINTER_COUNT; i++) {
			printers[i] = new Printer(i + 1, AMOUNT, PORTION);
		}
		
		for (int i = 0; i < PRINTER_COUNT; i++) {
			int nextNumber = (i + 1) % PRINTER_COUNT;
			printers[i].setNext(printers[nextNumber]);
		}
		
		Arrays.stream(printers).forEach(Printer::start);
		
		printers[0].interrupt();
	}
}
