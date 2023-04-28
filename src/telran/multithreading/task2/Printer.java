package telran.multithreading.task2;

public class Printer extends Thread {
	private Integer number;
	private int amount;
	private int portion;
	private Printer next;
	
	public Printer(int number, int amount, int portion) {
		this.number = number;
		this.amount = amount;
		this.portion = portion;
	}
	
	public void setNext(Printer next) {
		this.next = next;
	}
	
	@Override
	public void run() {
		int current = 0;
		while (current < amount) {
			try {
//				System.out.println(number + " join");
				join();
			} catch (InterruptedException e) {
				int numToPrint = Math.min(portion, amount - current);
				current += numToPrint;
				System.out.println(number.toString().repeat(numToPrint));
				next.interrupt();
			}
		}
	}
}
