package telran.pc.multithreading;

import java.util.LinkedList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import telran.multithreading.util.MyLinkedBlockingQueue;
import telran.pc.multithreading.consumers.ReceiverBQ;
import telran.pc.multithreading.producers.SenderBQ;

public class SendersReceiversBCAppl {

	private static final int N_MESSAGES = 2100;
	private static final int N_RECEIVERS = 10;
	private static LinkedList<ReceiverBQ> receivers = new LinkedList<>();
	public static AtomicInteger counterrr = new AtomicInteger(0);


	public static void main(String[] args) throws Exception {
		BlockingQueue<String> messageBox = new MyLinkedBlockingQueue<>(1);
		for (int i = 0; i < N_RECEIVERS; i++) {
			receivers.add(new ReceiverBQ(messageBox));
			receivers.getLast().start();
		}
		
//		Thread.sleep(1000);
		
		SenderBQ sender = new SenderBQ(messageBox, N_MESSAGES);
		sender.start();
		sender.join();
		receivers.forEach(r -> r.interrupt());
		receivers.forEach(r -> {
			try {
//				System.out.println("Start join: " + r.getName());
				r.join();
//				System.out.println("End join");
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
