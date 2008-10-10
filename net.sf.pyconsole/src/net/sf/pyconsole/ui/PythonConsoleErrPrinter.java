/**
 * 
 */
package net.sf.pyconsole.ui;

import org.eclipse.swt.custom.StyledText;

/**
 * @author Nirav Thaker
 * 
 */
class PythonConsoleErrPrinter extends PythonConsolePrinter {
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
			super.writeInternal(errString);
			this.pythonConsolePage.getStyledText().setData("ERR");
		}
	}
}