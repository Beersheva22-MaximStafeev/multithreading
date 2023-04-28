package telran.multithreading.util;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MyLinkedBlockingQueue<E> implements BlockingQueue<E> {
	
	private LinkedList<E> queue = new LinkedList<>();
	int limit;
	Lock lock = new ReentrantLock();
	Lock readLock = lock;
	Lock writeLock = lock;
	Condition readerWaiter = readLock.newCondition();
	Condition writerWaiter = writeLock.newCondition();
	
	public MyLinkedBlockingQueue(int limit) {
		this.limit = limit;
	}
	
	public MyLinkedBlockingQueue() {
		this(Integer.MAX_VALUE);
	}

	@Override
	public E remove() {
		writeLock.lock();
		try {
			if (!queue.isEmpty()) {
				E res = queue.remove();
				writerWaiter.signal();
				return res;
			}
			throw new NullPointerException();
		} finally {
			writeLock.unlock();
		}
	}

	@Override
	public E poll() {
		writeLock.lock();
		try {
			if (!queue.isEmpty()) {
				return remove();
			}
			return null;
		} finally {
			writeLock.unlock();
		}
	}

	@Override
	public E element() {
		readLock.lock();
		try {
			if (!queue.isEmpty()) {
				return queue.element();
			}
			throw new NullPointerException();
		} finally {
			readLock.unlock();
		}
	}

	@Override
	public E peek() {
		readLock.lock();
		try {
			return queue.peek();
		} finally {
			readLock.unlock();
		}
	}

	@Override
	public int size() {
		readLock.lock();
		try {
			return queue.size();
		} finally {
			 readLock.unlock();
		}
	}

	@Override
	public boolean isEmpty() {
		readLock.lock();
		try {
			return queue.isEmpty();
		} finally {
			 readLock.unlock();
		}
	}

	@Override
	public Iterator<E> iterator() {
		return queue.iterator();
	}

	@Override
	public Object[] toArray() {
		readLock.lock();
		try {
			return queue.toArray();
		} finally {
			 readLock.unlock();
		}
	}

	@Override
	public <T> T[] toArray(T[] a) {
		readLock.lock();
		try {
			return queue.toArray(a);
		} finally {
			 readLock.unlock();
		}
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		readLock.lock();
		try {
			return queue.containsAll(c);
		} finally {
			 readLock.unlock();
		}
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		writeLock.lock();
		try {
			c.forEach(el -> add(el));
			boolean res = !c.isEmpty();
			return res;
		} finally {
			 writeLock.unlock();
		}
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		writeLock.lock();
		try {
			boolean res = queue.removeAll(c);
			writerWaiter.signal();
			return res;
		} finally {
			 writeLock.unlock();
		}
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		readLock.lock();
		try {
			return queue.retainAll(c);
		} finally {
			 readLock.unlock();
		}
	}

	@Override
	public void clear() {
		writeLock.lock();
		try {
			queue.clear();
			writerWaiter.signal();
		} finally {
			writeLock.unlock();
		}
	}

	@Override
	public boolean add(E e) {
		writeLock.lock();
		try {
			if (queue.size() < limit) {
				boolean res = queue.add(e);
				readerWaiter.signal();
				return res;
			}
			throw new IllegalStateException();
		} finally {
			writeLock.unlock();
		}
	}

	@Override
	public boolean offer(E e) {
		writeLock.lock();
		try {
			if (queue.size() < limit) {
				boolean res = add(e);
				readerWaiter.signal();
				return res;
			}
			return false;
		} finally {
			writeLock.unlock();
		}
	}

	@Override
	public void put(E e) throws InterruptedException {
		writeLock.lock();
		try {
			while (queue.size() >= limit) {
				try {
					writerWaiter.await();
				} catch (InterruptedException e1) {
					if (queue.size() < limit) {
						break;
					}
				}
			}
			add(e);
			readerWaiter.signal();
		} finally {
			writeLock.unlock();
		}
	}

	@Override
	public boolean offer(E e, long timeout, TimeUnit unit) throws InterruptedException {
		writeLock.lock();
		try {
			Instant timeEnd = Instant.now().plus(timeout, unit.toChronoUnit());
			while (queue.size() >= limit && Instant.now().isBefore(timeEnd)) {
				writerWaiter.await(timeout, unit);
			}
			if (queue.size() < limit) {
				boolean res = add(e);
				readerWaiter.signal();
				return res;
			}
			return false;
		} finally {
			writeLock.unlock();
		}
	}

	@Override
	public E take() throws InterruptedException {
		readLock.lock();
		try {
			while (queue.isEmpty()) {
				readerWaiter.await();
			}
			E res = remove();
			return res;
		} finally {
			readLock.unlock();
		}
	}

	@Override
	public E poll(long timeout, TimeUnit unit) throws InterruptedException {
		readLock.lock();
		try {
			Instant timeEnd = Instant.now().plus(timeout, unit.toChronoUnit());
			while (queue.isEmpty() && Instant.now().isBefore(timeEnd)) {
				readerWaiter.await(timeout, unit);
			}
			E res = queue.poll();
			writerWaiter.signal();
			return res;
		} finally {
			readLock.unlock();
		}
	}

	@Override
	public int remainingCapacity() {
		readLock.lock();
		try {
			return limit - queue.size();
		} finally {
			readLock.unlock();
		}
	}

	@Override
	public boolean remove(Object o) {
		writeLock.lock();
		try {
			boolean res = queue.remove(o);
			writerWaiter.signal();
			return res;
		} finally {
			writeLock.unlock();
		}
	}

	@Override
	public boolean contains(Object o) {
		readLock.lock();
		try {
			return queue.contains(o);
		} finally {
			readLock.unlock();
		}
	}

	@Override
	public int drainTo(Collection<? super E> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int drainTo(Collection<? super E> c, int maxElements) {
		throw new UnsupportedOperationException();
	}

}
