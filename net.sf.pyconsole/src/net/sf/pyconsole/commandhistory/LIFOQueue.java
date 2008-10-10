/**
 * 
 */
package net.sf.pyconsole.commandhistory;

import java.util.Vector;

/**
 * @author Nirav Thaker
 * 
 */
public class LIFOQueue<E> extends Vector<E> {

	private static final long serialVersionUID = -1134557640702830554L;
	int pos;

	public LIFOQueue() {
		pos = this.size();
	}

	public LIFOQueue(int count) {
		super(count);
	}

	public E push(E arg0) {
		addElement(arg0);
		pos = this.size();
		return arg0;
	}

	public E next() {
		pos--;
		if (pos < 0)
			pos = this.size() - 1;
		E e = this.get(pos);
		return e;
	}

	public E previous() {
		pos++;
		if (pos >= this.size())
			pos = 0;
		E e = this.get(pos);
		return e;
	}

	public void reset() {
		pos = this.size();
	}

}
