package telran.multithreading.test;

import static org.junit.Assert.assertArrayEquals;

import java.util.Arrays;
import java.util.HashSet;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class ParallelStreamTest {
	Integer[] array = { 100, -10, 20, -90, 50, 40, 60, 100, -10 };
	Integer[] bigArray = ThreadLocalRandom.current().ints(10_000_000).mapToObj(el -> (Integer)el).toArray(Integer[]::new);
//	ArrayList<Integer> 

	@BeforeEach
	void setUp() throws Exception {

	}

	@Test
	@Disabled
	void test() {
		Integer[] expected = { -90, -10, -10, 20, 40, 50, 60, 100, 100 };
		HashSet<String> threadNames = new HashSet<>();
		int[] index = { 0 };
		Integer[] res = new Integer[expected.length];
		Arrays.stream(array).parallel()
		.sorted((a, b) -> {
//			threadNames.add(Thread.currentThread().getName());
			return Integer.compare(a, b);
		}).forEach(el -> {
			res[index[0]++] = el;
			threadNames.add(Thread.currentThread().getName());
		});
//		.toArray(Integer[]::new);
		threadNames.forEach(System.out::println);
//		System.out.println(threadNames);
		
		// test no works
		// if we change forEach to forEachOrdered, than test will pass 
		assertArrayEquals(expected, res);
	}
	
	@Test
	void bigTest() {
		long startTime = System.currentTimeMillis();
		HashSet<String> threadNames = new HashSet<>();
		Arrays.stream(bigArray)
		.parallel()
		.sorted((a, b) -> {
			threadNames.add(Thread.currentThread().getName());
			return Integer.compare(a, b);
		})
		.toArray();
		System.out.printf("millisecons passed: %d\n", System.currentTimeMillis() - startTime);
		System.out.println("Threads count: " + threadNames.size());
		threadNames.forEach(System.out::println);
	}
}
