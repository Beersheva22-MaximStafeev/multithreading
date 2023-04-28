package telran.pc.multithreading.producers;

import java.util.concurrent.BlockingQueue;

public class SenderBQ extends Thread {
	
	private BlockingQueue<String> messageBox;
	private int nMessages;
	
	public SenderBQ(BlockingQueue<String> messageBox, int nMessages) {
		this.messageBox = messageBox;
		this.nMessages = nMessages;
	}
	
	@Override
	public void run() {
		for (int i = 1; i <= nMessages; i++) {
			try {
//				System.out.println("putting...");
				messageBox.put("<tranfer message_" + i + ">");
//				System.out.println("putted...");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
