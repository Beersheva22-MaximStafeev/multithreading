package telran.pc.multithreading.consumers;

import java.util.concurrent.BlockingQueue;
import telran.pc.multithreading.SendersReceiversBCAppl;

public class ReceiverBQ extends Thread {
	
	private BlockingQueue<String> messageBox;
	
	public ReceiverBQ(BlockingQueue<String> messageBox) {
		this.messageBox = messageBox;
		this.setDaemon(false);
	}
	
	@Override
	public void run() {
		while (true) {
			try {
//				System.out.println("taking... "+ getName());
				String message = messageBox.take();
				printMessage(message);
			
			} catch (InterruptedException e) {
//				System.out.println("interrupted " + getName());
				String message = null;
				do {
//					System.out.println("peeking... "+ getName());
					message = messageBox.peek();
					if (message != null) {
						printMessage(message);
					}
				} while (message != null);
				break;
			}
		}
	}

	private void printMessage(String message) {
		System.out.printf("Thread: %s (%s), received message: %s, counter: %s\n", getId(), getName(), message, SendersReceiversBCAppl.counterrr.incrementAndGet());
//		SendersReceiversAppl.counterrr.incrementAndGet();
	}

}
