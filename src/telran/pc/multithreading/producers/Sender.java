package telran.pc.multithreading.producers;

import telran.pc.multithreading.MessageBox;
import telran.pc.multithreading.SendersReceiversAppl;

public class Sender extends Thread {
	
	private MessageBox[] messageBox;
	private int nMessages;
	
	public Sender(MessageBox[] messageBox, int nMessages) {
		this.messageBox = messageBox;
		this.nMessages = nMessages;
	}
	
	@Override
	public void run() {
		for (int i = 1; i <= nMessages; i++) {
			try {
				messageBox[SendersReceiversAppl.getIndex(i)].put("message " + i);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (SendersReceiversAppl.senderSleeping()) {
				try {
					sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
