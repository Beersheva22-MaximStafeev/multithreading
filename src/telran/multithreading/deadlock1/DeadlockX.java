package telran.multithreading.deadlock1;

public class DeadlockX {
	public static void main(String[] args) {
		X x1 = new X();
		X x2 = new X();
		x1.start();
		x2.start();
//		x1.join();
//		x2.join();
	}
}
