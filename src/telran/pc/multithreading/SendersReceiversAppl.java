package telran.pc.multithreading;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

import telran.pc.multithreading.consumers.Receiver;
import telran.pc.multithreading.producers.Sender;

public class SendersReceiversAppl {

	private static final int N_MESSAGES = 2100;
	private static final int N_RECEIVERS = 10;
	private static LinkedList<Receiver> receivers = new LinkedList<>();
	public static AtomicInteger counterrr = new AtomicInteger(0);


	public static void main(String[] args) throws Exception {
		MessageBox[] messageBox = new MessageBox[] {new MessageBox(), new MessageBox()};
		for (int i = 0; i < N_RECEIVERS; i++) {
			receivers.add(new Receiver(messageBox));
			receivers.getLast().start();
		}
		
//		Thread.sleep(1000);
		
		Sender sender = new Sender(messageBox, N_MESSAGES);
		sender.start();
		sender.join();
		receivers.forEach(r -> r.interrupt());
		receivers.forEach(r -> {
			try {
				r.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		System.out.println("All counter : " + counterrr.get());
		
	}
	
	public static boolean senderSleeping() {
		return false;
	}
	
	public static int getIndex(int i) {
//		return i % 2;
		return 0;
	}
	
}
