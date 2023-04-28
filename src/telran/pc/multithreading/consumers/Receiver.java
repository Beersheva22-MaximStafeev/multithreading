package telran.pc.multithreading.consumers;

import telran.pc.multithreading.MessageBox;
import telran.pc.multithreading.SendersReceiversAppl;

public class Receiver extends Thread {
	
	private MessageBox[] messageBox;
	
	public Receiver(MessageBox[] messageBox) {
		this.messageBox = messageBox;
		this.setDaemon(false);
	}
	
	@Override
	public void run() {
		// ver with daemon
//		while (true) {
//			try {
//				String message = messageBox[SendersReceiversAppl.getIndex((int) getId())].take();
//				System.out.printf("Thread: %s (%s), received message: %s\n", getId(), getName(), message);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//			
//		}
		
		// ver without daemon
		while (true) {
			try {
				String message = messageBox[SendersReceiversAppl.getIndex((int) getId())].take();
				System.out.printf("Thread: %s (%s), received message: %s\n", getId(), getName(), message);
				SendersReceiversAppl.counterrr.incrementAndGet();
			
			} catch (InterruptedException e) {
				String message = null;
				do {
					message = messageBox[SendersReceiversAppl.getIndex((int) getId())].get();
					if (message != null) {
						System.out.printf("Thread: %s (%s), received message: %s\n", getId(), getName(), message);
						SendersReceiversAppl.counterrr.incrementAndGet();
					}
				} while (message != null);
				break;
			}
		}
	}

}
