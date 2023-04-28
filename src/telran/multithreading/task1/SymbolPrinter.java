package telran.multithreading.task1;

public class SymbolPrinter extends Thread {
	private static final long DELAULT_TIMEOUT = 2000;
	private String symbols;
	private boolean running;
	
	public SymbolPrinter(String symbols) {
		this.symbols = symbols;
//		setDaemon(true);
	}
	
	@Override
	public void run() {
		// раз в 2 секунды печатает очередной символ из строки
		running = false;
		int curSymbol = 0;
		while (!running) {
			System.out.println(symbols.charAt(curSymbol));
			try {
				sleep(DELAULT_TIMEOUT);
			} catch (InterruptedException e) {
			}
			curSymbol = (curSymbol + 1) % symbols.length();
		}
	}

	public void quit() {
		interrupt();
		running = true;
		
	}
}
