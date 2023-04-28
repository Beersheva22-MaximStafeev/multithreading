package telran.multithreading.task1;

import java.util.Scanner;

public class SymbolPrinterAppl {
	public static void main(String[] args) {
		System.out.println("Enter string");
		Scanner scanner = new Scanner(System.in);
		String str = scanner.nextLine();
//		System.out.printf("[%s]", str);
		SymbolPrinter prn = new SymbolPrinter(str);
		prn.start();
		while (!scanner.nextLine().equals("q")) {
			prn.interrupt();
		}
		prn.quit();
		System.out.println("out");
		scanner.close();
	}
}
