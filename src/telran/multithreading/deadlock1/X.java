package telran.multithreading.deadlock1;

//import java.util.ArrayList;
////import java.util.Arrays;
//import java.util.Collection;
//import java.util.Collections;
//import java.util.HashSet;
//import java.util.List;
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.concurrent.ConcurrentLinkedQueue;
//import java.util.concurrent.locks.Lock;
//import java.util.concurrent.locks.ReentrantLock;

public class X extends Thread {
	final static Object[] res = new Object[1];
	final static Object res1 = new Object();
	final static Object res2 = new Object();
	final static Object res3 = new Object();
	
//	@SuppressWarnings("unused")
//	public void main1(String[] args) {
//		Lock lock = new ReentrantLock();
//		Thread.holdsLock(lock);
////		Thread.currentThread().holdsLock(lock);
//		ArrayList<String> ass = new ArrayList<>();
//		ConcurrentHashMap<Integer, String> a = new ConcurrentHashMap<>();
//		List<String> nnnn = Collections.synchronizedList(new ArrayList<String>());
//		ConcurrentLinkedQueue<String> list = new ConcurrentLinkedQueue<String>();
////		Collections.addAll(null, null)
//        Collection<?> collection = new HashSet<Object>();
//        Object object = new Object();
////        collection.addAll(Arrays.asList(object));
//        collection.remove(object);
//        collection.iterator();
//        collection.toArray();
////        collection.add(1);
//        collection.clear();
//        collection.size();
//        collection.contains(object);
//        Thread.currentThread().getState().describeConstable().get().bootstrapArgsList().get(0);
//        Thread.currentThread().st
//	}

	private void m1() {
		synchronized (res1) {
			synchronized (res2) {
				
			}
		}
	}
	
	private void m2( ) {
		synchronized (res2) {
			synchronized (res1) {
				
			}
		}
	}
	
	private void m3() {
		synchronized (res3) {
			
		}
	}
	
	@Override
	public void run() {
		for (int i = 0; i < 1099_000; i++) {
			m1();
			m2();
			m3();
		}
		System.out.printf("Thread %s end\n", this.getName());
	}
	
}
