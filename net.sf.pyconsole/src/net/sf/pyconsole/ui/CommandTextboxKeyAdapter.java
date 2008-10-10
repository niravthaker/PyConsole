/**
 * 
 */
package net.sf.pyconsole.ui;

import net.sf.pyconsole.commandhistory.CommandHistory;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.widgets.Text;

class CommandTextboxKeyAdapter extends KeyAdapter {
	private final PythonConsolePage pythonConsolePage;
	private final CommandHistory history;
	private final Text commandTextBox;

	public CommandTextboxKeyAdapter(PythonConsolePage pythonConsolePage,
			Text commandTextBox, CommandHistory history) {
		this.pythonConsolePage = pythonConsolePage;
		this.commandTextBox = commandTextBox;
		this.history = history;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.keyCode) {
		case SWT.ARROW_UP:
			String next = history.getNext();
			commandTextBox.setText(next);
			break;
		case SWT.ARROW_DOWN:
			String previous = history.getPrevious();
			commandTextBox.setText(previous);
			break;
		case '\t':
			handleTabKey(e);
			break;
		case 16777296:
		case '\r':
		case '\n':
			if (commandTextBox.getText().length() != 0)
				e.doit = false;
			else
				commandTextBox.setText("\n");
			this.pythonConsolePage.run();
		default:
		}
	}

	private void handleTabKey(KeyEvent e) {
		if (e.stateMask == SWT.SHIFT) {
			e.doit = false;
			String text = commandTextBox.getText();
			if (text.startsWith("\t")) {
				int indexOf = text.indexOf('\t') + 1;
				String substring = text.substring(indexOf);
				commandTextBox.setText(substring);
				commandTextBox.setSelection(commandTextBox.getText()
						.length());
			}

		} else {
			commandTextBox.setText('\t' + commandTextBox.getText());
			commandTextBox.setSelection(commandTextBox.getText().length());
			e.doit = false;
		}
	}
}