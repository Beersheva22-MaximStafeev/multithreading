package telran.multithreading.util;

import org.junit.jupiter.api.Test;

import java.util.*;


import java.util.concurrent.TimeUnit;
import static org.junit.jupiter.api.Assertions.*;

public class MyLinkedBlockingQueueTest {

    @Test
    public void testPut() throws InterruptedException {
    	 MyLinkedBlockingQueue<Integer> queue = new  MyLinkedBlockingQueue<>(2);
        queue.put(1);
        queue.put(2);
        assertEquals(2, queue.size());
        assertThrows(IllegalStateException.class, () -> queue.add(3));
    }

    @Test
    public void testTake() throws InterruptedException {
    	 MyLinkedBlockingQueue<Integer> queue = new  MyLinkedBlockingQueue<>();
        queue.put(1);
        assertEquals(1, queue.take());
    }

    @Test
    public void testPeek() throws InterruptedException {
    	 MyLinkedBlockingQueue<Integer> queue = new  MyLinkedBlockingQueue<>();
        queue.put(1);
        assertEquals(1, queue.peek());
        assertEquals(1, queue.size());
    }

    @Test
    public void testPoll() throws InterruptedException {
    	 MyLinkedBlockingQueue<Integer> queue = new  MyLinkedBlockingQueue<>();
        queue.put(1);
        assertEquals(1, queue.poll());
        assertEquals(0, queue.size());
    }

    @Test
    public void testPollWithTimeout() throws InterruptedException {
    	 MyLinkedBlockingQueue<Integer> queue = new  MyLinkedBlockingQueue<>();
        queue.put(1);
        assertEquals(1, queue.poll(500, TimeUnit.MILLISECONDS));
        assertEquals(0, queue.size());
    }
    
    @Test
    public void testOffer() throws InterruptedException {
    	 MyLinkedBlockingQueue<Integer> queue = new  MyLinkedBlockingQueue<>(2);
        assertTrue(queue.offer(1));
        assertTrue(queue.offer(2));
        assertFalse(queue.offer(3));
    }

    @Test
    public void testOfferWithTimeout() throws InterruptedException {
    	 MyLinkedBlockingQueue<Integer> queue = new  MyLinkedBlockingQueue<>(2);
        assertTrue(queue.offer(1, 500, TimeUnit.MILLISECONDS));
        assertTrue(queue.offer(2, 500, TimeUnit.MILLISECONDS));
        assertFalse(queue.offer(3, 500, TimeUnit.MILLISECONDS));
    }

    @Test
    public void testContains() throws InterruptedException {
    	 MyLinkedBlockingQueue<Integer> queue = new  MyLinkedBlockingQueue<>();
        queue.put(1);
        assertTrue(queue.contains(1));
        assertFalse(queue.contains(2));
    }

    @Test
    public void testRemove() throws InterruptedException {
    	 MyLinkedBlockingQueue<Integer> queue = new  MyLinkedBlockingQueue<>();
        queue.put(1);
        assertTrue(queue.remove(1));
        assertFalse(queue.remove(2));
    }

   
    @Test
    public void testClear() throws InterruptedException {
    	 MyLinkedBlockingQueue<Integer> queue = new  MyLinkedBlockingQueue<>();
        queue.put(1);
        queue.put(2);
        queue.put(3);
        queue.clear();
        assertEquals(0, queue.size());
    }
    
    @Test
    public void testRemainingCapacity() throws InterruptedException {
    	MyLinkedBlockingQueue<Integer> queue = new MyLinkedBlockingQueue<>(5);
        queue.put(1);
        queue.put(2);
        assertEquals(3, queue.remainingCapacity());
    }

    @Test
    public void testToArray() throws InterruptedException {
    	MyLinkedBlockingQueue<Integer> queue = new MyLinkedBlockingQueue<>();
        queue.put(1);
        queue.put(2);
        assertArrayEquals(new Object[]{1, 2}, queue.toArray());
    }

    @Test
    public void testIterator() throws InterruptedException {
    	MyLinkedBlockingQueue<Integer> queue = new MyLinkedBlockingQueue<>();
        queue.put(1);
        queue.put(2);
        Iterator<Integer> iterator = queue.iterator();
        assertTrue(iterator.hasNext());
        assertEquals(1, iterator.next());
        assertEquals(2, iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void testContainsAll() throws InterruptedException {
    	MyLinkedBlockingQueue<Integer> queue = new MyLinkedBlockingQueue<>();
        queue.put(1);
        queue.put(2);
        queue.put(3);
        assertTrue(queue.containsAll(Arrays.asList(1, 2, 3)));
        assertFalse(queue.containsAll(Arrays.asList(1, 2, 4)));
    }
    
    @Test
    public void testElement() throws InterruptedException {
    	MyLinkedBlockingQueue<Integer> queue = new MyLinkedBlockingQueue<>();
        queue.put(1);
        assertEquals(1, queue.element());
    }

    @Test
    public void testRemoveObject() throws InterruptedException {
    	MyLinkedBlockingQueue<Integer> queue = new MyLinkedBlockingQueue<>();
        queue.put(1);
        assertTrue(queue.remove(1));
        assertFalse(queue.remove(1));
    }

    @Test
    public void testAddAll() {
    	MyLinkedBlockingQueue<Integer> queue = new MyLinkedBlockingQueue<>();
        Collection<Integer> collection = new ArrayList<>();
        collection.add(1);
        collection.add(2);
        assertTrue(queue.addAll(collection));
        assertEquals(2, queue.size());
    }

    @Test
    public void testRemoveAll() throws InterruptedException {
    	MyLinkedBlockingQueue<Integer> queue = new MyLinkedBlockingQueue<>();
        queue.put(1);
        queue.put(2);
        Collection<Integer> collection = new ArrayList<>();
        collection.add(1);
        collection.add(2);
        assertTrue(queue.removeAll(collection));
        assertTrue(queue.isEmpty());
    }

    @Test
    public void testRetainAll() throws InterruptedException {
    	MyLinkedBlockingQueue<Integer> queue = new MyLinkedBlockingQueue<>();
        queue.put(1);
        queue.put(2);
        Collection<Integer> collection = new ArrayList<>();
        collection.add(1);
        assertTrue(queue.retainAll(collection));
        assertEquals(1, queue.size());
        assertEquals(1, queue.element());
    }

    @Test
    public void testIsEmpty() throws InterruptedException {
    	MyLinkedBlockingQueue<Integer> queue = new MyLinkedBlockingQueue<>();
        assertTrue(queue.isEmpty());
        queue.put(1);
        assertFalse(queue.isEmpty());
    }
    
    @Test
    public void testAdd() {
    	MyLinkedBlockingQueue<Integer> queue = new MyLinkedBlockingQueue<>(3);
        assertTrue(queue.add(1));
        assertTrue(queue.add(2));
        assertTrue(queue.add(8));
        assertThrows(IllegalStateException.class, () -> queue.add(4));
    }

    @Test
    public void testSize() throws InterruptedException {
    	MyLinkedBlockingQueue<Integer> queue = new MyLinkedBlockingQueue<>();
        queue.put(1);
        queue.put(2);
        assertEquals(2, queue.size());
        queue.take();
        assertEquals(1, queue.size());
    }
}