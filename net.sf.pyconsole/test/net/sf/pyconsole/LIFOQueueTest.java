package net.sf.pyconsole;

import junit.framework.TestCase;
import net.sf.pyconsole.commandhistory.LIFOQueue;

/**
 * @author Nirav Thaker
 * 
 */
public class LIFOQueueTest extends TestCase {

	public void testNext() {
		LIFOQueue<String> queue = new LIFOQueue<String>();
		queue.push("Abc");
		queue.push("Pqr");
		queue.push("Xyz");
		assertEquals("Xyz", queue.next());
		assertEquals("Pqr", queue.next());
		assertEquals("Abc", queue.next());

		assertEquals("Xyz", queue.next());
		assertEquals("Pqr", queue.next());
		assertEquals("Abc", queue.next());

		assertEquals("Xyz", queue.next());
		assertEquals("Pqr", queue.next());
		assertEquals("Abc", queue.next());
	}

	public void testPrevious() {
		LIFOQueue<String> queue = new LIFOQueue<String>();
		queue.push("Abc");
		queue.push("Pqr");
		queue.push("Xyz");
		assertEquals("Abc", queue.previous());
		assertEquals("Pqr", queue.previous());
		assertEquals("Xyz", queue.previous());

		assertEquals("Pqr", queue.next());
		assertEquals("Abc", queue.next());
		assertEquals("Xyz", queue.next());

		assertEquals("Abc", queue.previous());
		assertEquals("Pqr", queue.previous());
		assertEquals("Abc", queue.next());
	}
}
