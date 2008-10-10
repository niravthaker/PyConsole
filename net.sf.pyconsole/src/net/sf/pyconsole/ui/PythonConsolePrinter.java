/**
 * 
 */
package net.sf.pyconsole.ui;

import net.sf.pyconsole.StylePrinter;

import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Display;

class PythonConsolePrinter implements StylePrinter {
	protected StyledText text;
	protected StringBuffer buffer;

	public PythonConsolePrinter(StyledText stest) {
		text = stest;
	}

	public void setText(StyledText text) {
		this.text = text;
	}

	@Override
	public void write(final String str) {
		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				writeInternal(str);
			}

		});
	}

	protected void writeInternal(String str) {
		append(text, str);
	}

	public static void append(StyledText text, String str) {
		if (text != null && !text.isDisposed()) {
			int lineCount = text.getLineCount();
			text.setTopIndex(lineCount - 1);
		}
		text.append(str + "\r\n");
		text.setData("OUT");
	}
}