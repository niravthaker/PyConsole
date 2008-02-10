/**
 * 
 */
package net.sf.pyconsole.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;

class PythonConsoleErrPrinter extends PythonConsolePrinter {
	/**
	 * 
	 */
	private final PythonConsolePage pythonConsolePage;

	public PythonConsoleErrPrinter(PythonConsolePage pythonConsolePage,
			StyledText stest) {
		super(stest);
		this.pythonConsolePage = pythonConsolePage;
	}

	@Override
	public void writeInternal(String errString) {
		if (errString.length() > 0) {
			errString = errString.replace(">>>", "");
			StyleRange range = new StyleRange();
			range.start = this.pythonConsolePage.styledText.getText().length();
			range.length = errString.length();
			range.foreground = this.pythonConsolePage.styledText.getDisplay()
					.getSystemColor(SWT.COLOR_RED);
			range.background = null;
			super.writeInternal(errString);
			this.pythonConsolePage.styledText.setStyleRange(range);
		}
	}
}